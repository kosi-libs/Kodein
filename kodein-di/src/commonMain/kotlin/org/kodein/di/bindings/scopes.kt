package org.kodein.di.bindings

import kotlinx.atomicfu.locks.SynchronizedObject
import org.kodein.di.DIContext
import org.kodein.di.DirectDI
import org.kodein.di.internal.maySynchronized
import org.kodein.di.internal.newConcurrentMap
import org.kodein.di.internal.synchronizedIfNotNull
import org.kodein.di.internal.synchronizedIfNull
import org.kodein.type.TypeToken

@Deprecated(
    message = "Use kotlin AutoCloseable instead.",
    replaceWith = ReplaceWith("AutoCloseable", imports = arrayOf("AutoCloseable"))
)
public typealias ScopeCloseable = AutoCloseable

private typealias RegKey = Any

/**
 * A registry is responsible managing references inside a scope.
 */
@Suppress("DEPRECATION")
public sealed class ScopeRegistry : ScopeCloseable {
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
    public abstract fun getOrCreate(key: RegKey, sync: Boolean = true, creator: () -> Reference<Any>): Any

    public abstract fun getOrNull(key: RegKey): (() -> Any?)?

    public abstract fun values(): Iterable<Pair<RegKey, () -> Any?>>

    public abstract fun remove(key: RegKey)

    public abstract fun clear()

    final override fun close(): Unit = clear()
}

/**
 * Standard [ScopeRegistry] implementation.
 */
public class StandardScopeRegistry : ScopeRegistry() {

    private val _cache = newConcurrentMap<RegKey, () -> Any?>()

    private val _lock = SynchronizedObject()

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

    override fun getOrNull(key: RegKey): (() -> Any?)? = _cache[key]

    override fun values(): List<Pair<RegKey, () -> Any?>> = _cache.map { it.toPair() }

    override fun remove(key: RegKey) {
        (_cache.remove(key)?.invoke() as? AutoCloseable)?.close()
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
            (it.invoke() as? AutoCloseable)?.close()
        }
    }

    /**
     * The number of singleton objects currently created in this scope.
     */
    public val size: Int get() = _cache.size

    /**
     * @return Whether or not this scope is empty (contains no singleton objects).
     */
    public fun isEmpty(): Boolean = _cache.isEmpty()
}

/**
 * [ScopeRegistry] that is specialized to hold only one item.
 *
 * If the key changes, the held item will be replaced.
 */
public class SingleItemScopeRegistry : ScopeRegistry() {
    private val _lock = SynchronizedObject()

    @kotlin.concurrent.Volatile
    private var _pair: Pair<RegKey, () -> Any?>? = null

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
        (oldRef?.invoke() as? AutoCloseable)?.close()
        return value
    }

    override fun getOrNull(key: RegKey): (() -> Any?)? = _pair?.let { (pKey, pRef) -> if (key == pKey) pRef else null }

    /**
     * @return Whether or not this scope is empty (contains no item).
     */
    public fun isEmpty(): Boolean = _pair == null

    override fun values(): List<Pair<RegKey, () -> Any?>> = _pair?.let { listOf(it) } ?: emptyList()

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

        (ref?.invoke() as? AutoCloseable)?.close()
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

        (ref?.invoke() as? AutoCloseable)?.close()
    }
}

public interface ContextTranslator<in C : Any, S : Any> {
    public val contextType: TypeToken<in C>
    public val scopeType: TypeToken<in S>
    public fun translate(di: DirectDI, ctx: C): S?
}

public class SimpleContextTranslator<in C : Any, S: Any>(override val contextType: TypeToken<in C>, override val scopeType: TypeToken<in S>, private val t: DirectDI.(ctx: C) -> S?) : ContextTranslator<C, S> {
    override fun translate(di: DirectDI, ctx: C): S? = di.t(ctx)
    override fun toString(): String = "()"
}

public class SimpleAutoContextTranslator<S: Any>(override val scopeType: TypeToken<in S>, private val t: DirectDI.() -> S) : ContextTranslator<Any, S> {
    override val contextType: TypeToken<Any> get() = TypeToken.Any
    override fun translate(di: DirectDI, ctx: Any): S = di.t()
    override fun toString(): String = "(${scopeType.simpleDispString()} -> ${contextType.simpleDispString()})"
}

public fun <C : Any, S: Any> ContextTranslator<C, S>.toKContext(di: DirectDI, ctx: C): DIContext<S>? = translate(di, ctx)?.let { DIContext(scopeType, it) }

internal class CompositeContextTranslator<in C : Any, I : Any, S: Any>(val src: ContextTranslator<C, I>, val dst: ContextTranslator<I, S>) : ContextTranslator<C, S> {
    override val contextType get() = src.contextType
    override val scopeType get() = dst.scopeType
    override fun translate(di: DirectDI, ctx: C): S? = src.translate(di, ctx)?.let { dst.translate(di, it) }
    override fun toString() = "($src -> $dst)"
}


/**
 * A scope is an object that can return (or create) a [ScopeRegistry] according to a context.
 *
 * @param C The Context.
 */
public interface Scope<in C> {

    /**
     * Get a registry for a given context.
     * Should always return the same registry for the same context.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    public fun getRegistry(context: C): ScopeRegistry
}

/**
 * [Scope] that is not bound to a context (always lives).
 *
 * This is kind of equivalent to having no scope at all, except that you can call [clear].
 */
public open class UnboundedScope(public val registry: ScopeRegistry = StandardScopeRegistry()) : Scope<Any?>, AutoCloseable {
    override fun getRegistry(context: Any?): ScopeRegistry = registry

    override fun close(): Unit = registry.clear()
}

public abstract class SubScope<C, PC>(private val parentScope: Scope<PC>) : Scope<C> {

    private data class Key<C>(val context: C)

    protected abstract fun getParentContext(context: C): PC

    override fun getRegistry(context: C): ScopeRegistry {
        val parentRegistry = parentScope.getRegistry(getParentContext(context))
        @Suppress("UNCHECKED_CAST")
        return parentRegistry.getOrCreate(Key(context), false) { SingletonReference.make { newRegistry() } } as ScopeRegistry
    }

    public open fun newRegistry(): ScopeRegistry = StandardScopeRegistry()
}

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
public class NoScope: Scope<Any?> {

    private val _registry = StandardScopeRegistry()

    override fun getRegistry(context: Any?): StandardScopeRegistry = _registry
}
