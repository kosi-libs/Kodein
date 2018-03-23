package org.kodein.di.bindings

import org.kodein.di.Volatile
import org.kodein.di.internal.newConcurrentMap
import org.kodein.di.internal.synchronizedIfNull

/**
 * A registry is responsible managing references inside a scope.
 */
interface ScopeRegistry {
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
    fun getOrCreate(key: Any, creator: () -> Reference<Any>): Any
}


/**
 * Standard [ScopeRegistry] implementation.
 */
class MultiItemScopeRegistry : ScopeRegistry {

    private val _cache = newConcurrentMap<Any, () -> Any?>()

    private val _lock = Any()

    override fun getOrCreate(key: Any, creator: () -> Reference<Any>): Any {
        return synchronizedIfNull(
                lock = _lock,
                predicate = { _cache[key]?.invoke() },
                ifNotNull = { it },
                ifNull = {
                    val (current, next) = creator()
                    _cache[key] = next
                    current
                }
        )
    }

    /**
     * Remove all objects from the scope.
     */
    fun clear() = _cache.clear()

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
 */
class SingleItemScopeRegistry : ScopeRegistry {
    @Volatile private var _ref: () -> Any? = { null }
    private val _lock = Any()
    private var _key: Any? = null

    override fun getOrCreate(key: Any, creator: () -> Reference<Any>): Any {
        synchronizedIfNull(
                lock = _lock,
                predicate = { _key },
                ifNotNull = {
                    if (it != key)
                        throw IllegalStateException("SingleItemScopeRegistry must always receive the same key.\n$key != $it")
                },
                ifNull = { _key = key }
        )

        return synchronizedIfNull(
                lock = _lock,
                predicate = { _ref() },
                ifNotNull = { it },
                ifNull = {
                    val (value, later) = creator()
                    _ref = later
                    value
                }
        )
    }

    /**
     * @return Whether or not this scope is empty (contains no item).
     */
    fun isEmpty(): Boolean = _key == null

    /**
     * Remove the item & reset the scope.
     */
    fun clear() {
        _key = null
        _ref = { null }
    }
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
}

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
class NoScope: Scope<Any?, Nothing?> {

    override fun getBindingContext(envContext: Any?): Nothing? = null

    private val _registry by lazy { MultiItemScopeRegistry() }

    override fun getRegistry(receiver: Any?, context: Any?) = _registry
}
