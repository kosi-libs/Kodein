package org.kodein.di.bindings

import org.kodein.di.Volatile
import org.kodein.di.internal.*

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

// Deprecated since 5.4.0
@Deprecated("MultiItemScopeRegistry has been renamed StandardScopeRegistry", ReplaceWith("StandardScopeRegistry"))
typealias MultiItemScopeRegistry = StandardScopeRegistry

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

@Deprecated("Use directly StandardScopeRegistry or SingleItemScopeRegistry constructors")
enum class ScopeRepositoryType {
    MULTI_ITEM,
    SINGLE_ITEM
}

@Suppress("DEPRECATION", "DeprecatedCallableAddReplaceWith")
@Deprecated("Use directly StandardScopeRegistry or SingleItemScopeRegistry constructors")
fun <A> newScopeRegistry(type: ScopeRepositoryType) = when (type) {
    ScopeRepositoryType.MULTI_ITEM -> StandardScopeRegistry()
    ScopeRepositoryType.SINGLE_ITEM -> SingleItemScopeRegistry()
}


/**
 * A scope is an object that can return (or create) a [ScopeRegistry] according to a context.
 *
 * @param EC The Environment Context: That's the context that is given to the scope from the retriever code.
 * @param BC The Binding Context: That's the context that is given by the scope to the bindings.
 *   It is often the same as [EC], in which case you should use [SimpleScope] instead.
 */
interface Scope<in EC, out BC> {

    /**
     * Get the Binding Context (that may or may not be derived from [envContext]).
     *
     * @param envContext The context that was given to the scope from the retriever code.
     */
    fun getBindingContext(envContext: EC): BC

    /**
     * Get a registry for a given context.
     * Should always return the same registry for the same tuple receiver / envContext / bindContext.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    fun getRegistry(receiver: Any?, context: EC): ScopeRegistry
}

/**
 * Simple [Scope] where the Environment Context and the Binding Context do not differ.
 */
interface SimpleScope<C> : Scope<C, C> {
    override fun getBindingContext(envContext: C) = envContext

    @Suppress("UNCHECKED_CAST")
    operator fun <T: C> invoke() = this as SimpleScope<T>

}

/**
 * [Scope] that is not bound to a context (always lives).
 *
 * This is kind of equivalent to having no scope at all, except that you can call [clear].
 */
class UnboundedScope(val registry: ScopeRegistry = StandardScopeRegistry()) : SimpleScope<Any?>, ScopeCloseable {
    override fun getRegistry(receiver: Any?, context: Any?) = registry

    override fun close() = registry.clear()
}

// Deprecated since 5.4.0
@Deprecated("BasicScope has been renamed UnboundedScope", ReplaceWith("UnboundedScope"))
typealias BasicScope = UnboundedScope

abstract class SubScope<in EC, BC>(val parentScope: Scope<BC, Any?>) : Scope<EC, BC> {

    private data class Key<C>(val context: C)

    override fun getRegistry(receiver: Any?, context: EC): ScopeRegistry {
        val bindingContext = getBindingContext(context)
        val parentRegistry = parentScope.getRegistry(receiver, bindingContext)
        @Suppress("UNCHECKED_CAST")
        return parentRegistry.getOrCreate(Key(context), false) { SingletonReference.make { newRegistry() } } as ScopeRegistry
    }

    fun removeFromParent(receiver: Any?, context: EC) {
        val bindingContext = getBindingContext(context)
        val parentRegistry = parentScope.getRegistry(receiver, bindingContext)
        parentRegistry.remove(Key(context))
    }

    open fun newRegistry(): ScopeRegistry = StandardScopeRegistry()
}

open class SimpleSubScope<C>(parentScope: Scope<C, Any?>) : SimpleScope<C>, SubScope<C, C>(parentScope)

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
class NoScope: Scope<Any?, Nothing?> {

    override fun getBindingContext(envContext: Any?): Nothing? = null

    private val _registry = StandardScopeRegistry()

    override fun getRegistry(receiver: Any?, context: Any?) = _registry
}
