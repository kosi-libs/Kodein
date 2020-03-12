package org.kodein.di.bindings

import org.kodein.di.DIContext
import org.kodein.di.Volatile
import org.kodein.di.internal.maySynchronized
import org.kodein.di.internal.newConcurrentMap
import org.kodein.di.internal.synchronizedIfNotNull
import org.kodein.di.internal.synchronizedIfNull
import org.kodein.type.TypeToken

interface ScopeCloseable {
    fun close()
}

private typealias RegKey = Any

/**
 * A registry is responsible managing references inside a scope.
 */
sealed class ScopeRegistry : ScopeCloseable {
    /**
     * Get or create a value that correspond for the given key.
     *
     * This function should operate as follow:
     *
     * - If there is a function associated to the [key], call it, and if the result is not empty, return it.
     * - If there is no function associated, or if the function returned null:
     *   * Call the creator function
     *   * Store the [Reference.next] function and associate it to the key for future requests.
     *   * Return the [Reference.current] value.
     *
     * @param key An object representing a "key" to associate.
     * @param creator A function that creates a reference that will be stored in the registry.
     * @return A value associated to the [key], whether created by [creator] or retrieved by [Reference.next].
     */
    abstract fun getOrCreate(key: RegKey, sync: Boolean = true, creator: () -> Reference<Any>): Any

    abstract fun getOrNull(key: RegKey): (() -> Any?)?

    abstract fun values(): Iterable<Pair<RegKey, () -> Any?>>

    abstract fun remove(key: RegKey)

    abstract fun clear()

    final override fun close() = clear()
}

/**
 * Standard [ScopeRegistry] implementation.
 */
class StandardScopeRegistry : ScopeRegistry() {

    private val _cache = newConcurrentMap<RegKey, () -> Any?>()

    private val _lock = Any()

    override fun getOrCreate(key: RegKey, sync: Boolean, creator: () -> Reference<Any>): Any {
        return synchronizedIfNull(
                lock = if (sync) _lock else null,
                predicate = { _cache[key]?.invoke() },
                ifNotNull = { it },
                ifNull = {
                    val (current, next) = creator()
                    _cache[key] = next
                    current
                }
        )
    }

    override fun getOrNull(key: RegKey) = _cache[key]

    override fun values() = _cache.map { it.toPair() }

    override fun remove(key: RegKey) {
        (_cache.remove(key)?.invoke() as? ScopeCloseable)?.close()
    }

    /**
     * Remove all objects from the scope.
     */
    override fun clear() {
        val refs = maySynchronized(_lock) {
            val refs = _cache.values.toList()
            _cache.clear()
            refs
        }
        refs.forEach {
            (it.invoke() as? ScopeCloseable)?.close()
        }
    }

    /**
     * The number of singleton objects currently created in this scope.
     */
    val size: Int get() = _cache.size

    /**
     * @return Whether or not this scope is empty (contains no singleton objects).
     */
    fun isEmpty(): Boolean = _cache.isEmpty()
}

/**
 * [ScopeRegistry] that is specialized to hold only one item.
 *
 * If the key changes, the held item will be replaced.
 */
class SingleItemScopeRegistry : ScopeRegistry() {
    private val _lock = Any()
    @Volatile private var _pair: Pair<RegKey, () -> Any?>? = null

    override fun getOrCreate(key: RegKey, sync: Boolean, creator: () -> Reference<Any>): Any {
        val (oldRef, value) = synchronizedIfNull(
                lock = if (sync) _lock else null,
                predicate = { _pair?.let { (pKey, pRef) -> if (key == pKey) pRef() else null } },
                ifNotNull = { null to it },
                ifNull = {
                    val oldRef = _pair?.second
                    val (value, ref) = creator()
                    _pair = key to ref
                    oldRef to value
                }
        )
        (oldRef?.invoke() as? ScopeCloseable)?.close()
        return value
    }

    override fun getOrNull(key: RegKey) = _pair?.let { (pKey, pRef) -> if (key == pKey) pRef else null }

    /**
     * @return Whether or not this scope is empty (contains no item).
     */
    fun isEmpty(): Boolean = _pair == null

    override fun values() = _pair?.let { listOf(it) } ?: emptyList()

    override fun remove(key: RegKey) {
        val ref = synchronizedIfNotNull(
                lock = _lock,
                predicate = { _pair },
                ifNull = { null },
                ifNotNull = { (pKey, pRef) ->
                    if (pKey != key)
                        throw IllegalStateException("SingleItemScopeRegistry currently holds a different key\n$key != $pKey")
                    _pair = null
                    pRef
                }
        )

        (ref?.invoke() as? ScopeCloseable)?.close()
    }

    /**
     * Remove the item & reset the scope.
     */
    override fun clear() {
        val ref = synchronizedIfNotNull(
                lock = _lock,
                predicate = { _pair },
                ifNull = { null },
                ifNotNull = { (_, pRef) ->
                    _pair = null
                    pRef
                }
        )

        (ref?.invoke() as? ScopeCloseable)?.close()
    }
}

interface ContextTranslator<in C : Any, S : Any> {
    val contextType: TypeToken<in C>
    val scopeType: TypeToken<in S>
    fun translate(ctx: C): S
}

class SimpleContextTranslator<in C : Any, S: Any>(override val contextType: TypeToken<in C>, override val scopeType: TypeToken<in S>, private val t: (ctx: C) -> S) : ContextTranslator<C, S> {
    override fun translate(ctx: C): S = t(ctx)
    override fun toString() = "()"
}

class SimpleAutoContextTranslator<S: Any>(override val scopeType: TypeToken<in S>, private val t: () -> S) : ContextTranslator<Any, S> {
    override val contextType get() = TypeToken.Any
    override fun translate(ctx: Any): S = t()
    override fun toString() = "(${scopeType.simpleDispString()} -> ${contextType.simpleDispString()})"
}

fun <C : Any, S: Any> ContextTranslator<C, S>.toKContext(ctx: C) = DIContext(scopeType, translate(ctx))

internal class CompositeContextTranslator<in C : Any, I : Any, S: Any>(val src: ContextTranslator<C, I>, val dst: ContextTranslator<I, S>) : ContextTranslator<C, S> {
    override val contextType get() = src.contextType
    override val scopeType get() = dst.scopeType
    override fun translate(ctx: C): S = dst.translate(src.translate(ctx))
    override fun toString() = "($src -> $dst)"
}


/**
 * A scope is an object that can return (or create) a [ScopeRegistry] according to a context.
 *
 * @param C The Context.
 */
interface Scope<in C> {

    /**
     * Get a registry for a given context.
     * Should always return the same registry for the same tuple envContext / bindContext.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    fun getRegistry(context: C): ScopeRegistry
}

/**
 * [Scope] that is not bound to a context (always lives).
 *
 * This is kind of equivalent to having no scope at all, except that you can call [clear].
 */
open class UnboundedScope(val registry: ScopeRegistry = StandardScopeRegistry()) : Scope<Any?>, ScopeCloseable {
    override fun getRegistry(context: Any?) = registry

    override fun close() = registry.clear()
}

abstract class SubScope<C, PC>(val parentScope: Scope<PC>) : Scope<C> {

    private data class Key<C>(val context: C)

    protected abstract fun getParentContext(context: C): PC

    override fun getRegistry(context: C): ScopeRegistry {
        val parentRegistry = parentScope.getRegistry(getParentContext(context))
        @Suppress("UNCHECKED_CAST")
        return parentRegistry.getOrCreate(Key(context), false) { SingletonReference.make { newRegistry() } } as ScopeRegistry
    }

    open fun newRegistry(): ScopeRegistry = StandardScopeRegistry()
}

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
class NoScope: Scope<Any?> {

    private val _registry = StandardScopeRegistry()

    override fun getRegistry(context: Any?) = _registry
}
