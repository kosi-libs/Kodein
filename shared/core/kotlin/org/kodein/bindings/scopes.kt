package org.kodein.bindings

import org.kodein.internal.synchronizedIfNull
import org.kodein.newConcurrentMap


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

interface Scope<in C> {

    /**
     * Get a registry for a given context. Should always return the same registry for the same receiver / context.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    fun getRegistry(receiver: Any?, context: C): ScopeRegistry

    fun getHolder(receiver: Any?, context: C): ScopeHolder {
        val key = Any()
        val registry = getRegistry(receiver, context)
        return object : ScopeHolder {
            override fun getOrCreate(creator: () -> Pair<Any, () -> Any?>) = registry.getOrCreate(key, creator)
        }
    }
}

class NoScope: Scope<Any?> {
    val registry by lazy { MultiItemScopeRegistry() }
    override fun getRegistry(receiver: Any?, context: Any?) = registry

    val holder by lazy { SingleItemScopeRegistry() }
    override fun getHolder(receiver: Any?, context: Any?) = holder
}
