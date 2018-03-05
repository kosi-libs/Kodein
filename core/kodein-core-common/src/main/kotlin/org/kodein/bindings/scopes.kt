package org.kodein.bindings

import org.kodein.internal.newConcurrentMap
import org.kodein.internal.synchronizedIfNull


interface ScopeRegistry {
    fun getOrCreate(key: Any?, creator: () -> Pair<Any, () -> Any?>): Any
}

interface ScopeHolder {
    fun getOrCreate(creator: () -> Pair<Any, () -> Any?>): Any
}

private object NullArg

class MultiItemScopeRegistry : ScopeRegistry {
    /**
     * Map of stored objects
     */
    private val _cache = newConcurrentMap<Any, () -> Any?>()

    private val _lock = Any()

    override fun getOrCreate(key: Any?, creator: () -> Pair<Any, () -> Any?>): Any {
        val realKey = key ?: NullArg
        return synchronizedIfNull(
                lock = _lock,
                predicate = { _cache[realKey]?.invoke() },
                ifNotNull = { it },
                ifNull = {
                    val (value, later) = creator()
                    _cache[realKey] = later
                    value
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

class SingleItemScopeRegistry : ScopeRegistry, ScopeHolder {
    private @Volatile var _ref: () -> Any? = { null }
    private val _lock = Any()

    override fun getOrCreate(key: Any?, creator: () -> Pair<Any, () -> Any?>) = getOrCreate(creator)

    override fun getOrCreate(creator: () -> Pair<Any, () -> Any?>): Any {
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

    fun clear() {
        _ref = { null }
    }
}

private fun <EC, BC> Scope<EC, BC>._defaultHolder(receiver: Any?, envContext: EC, bindContext: BC): ScopeHolder {
    val key = Any()
    val registry = getRegistry(receiver, envContext, bindContext)
    return object : ScopeHolder {
        override fun getOrCreate(creator: () -> Pair<Any, () -> Any?>) = registry.getOrCreate(key, creator)
    }
}

interface Scope<in EC, BC> {

    fun getBindingContext(envContext: EC): BC

    /**
     * Get a registry for a given context. Should always return the same registry for the same receiver / context.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    fun getRegistry(receiver: Any?, envContext: EC, bindContext: BC): ScopeRegistry

    fun getHolder(receiver: Any?, envContext: EC, bindContext: BC): ScopeHolder = _defaultHolder(receiver, envContext, bindContext)
}

interface SimpleScope<C> : Scope<C, C> {
    override fun getBindingContext(envContext: C) = envContext

    fun getRegistry(receiver: Any?, context: C): ScopeRegistry
    override fun getRegistry(receiver: Any?, envContext: C, bindContext: C) = getRegistry(receiver, envContext)

    fun getHolder(receiver: Any?, context: C): ScopeHolder = _defaultHolder(receiver, context, context)
    override fun getHolder(receiver: Any?, envContext: C, bindContext: C) = getHolder(receiver, envContext)
}

class NoScope: Scope<Any?, Nothing?> {

    override fun getBindingContext(envContext: Any?) = null

    val registry by lazy { MultiItemScopeRegistry() }
    override fun getRegistry(receiver: Any?, envContext: Any?, bindContext: Nothing?) = registry

    val holder by lazy { SingleItemScopeRegistry() }
    override fun getHolder(receiver: Any?, envContext: Any?, bindContext: Nothing?) = holder
}
