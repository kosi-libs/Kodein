package org.kodein.di.bindings

import org.kodein.di.Volatile
import org.kodein.di.internal.newConcurrentMap
import org.kodein.di.internal.synchronizedIfNotNull
import org.kodein.di.internal.synchronizedIfNull

/**
 * A registry is responsible managing references inside a scope.
 */
interface ScopeRegistry<A> {
    interface Key<out A> {
        val arg: A
    }


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
    fun getOrCreate(key: ScopeRegistry.Key<A>, creator: () -> Reference<Any>): Any

    fun getOrNull(key: ScopeRegistry.Key<A>): (() -> Any?)?

    fun values(): Iterable<Pair<ScopeRegistry.Key<A>, () -> Any?>>

    fun remove(key: ScopeRegistry.Key<A>)

    fun clear()
}

interface ScopeCloseable {
    fun close()
}

/**
 * Standard [ScopeRegistry] implementation.
 */
class MultiItemScopeRegistry<A> : ScopeRegistry<A> {

    private val _cache = newConcurrentMap<ScopeRegistry.Key<A>, () -> Any?>()

    private val _lock = Any()

    override fun getOrCreate(key: ScopeRegistry.Key<A>, creator: () -> Reference<Any>): Any {
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

    override fun getOrNull(key: ScopeRegistry.Key<A>) = _cache[key]

    override fun values() = _cache.map { it.toPair() }

    override fun remove(key: ScopeRegistry.Key<A>) {
        (_cache.remove(key)?.invoke() as? ScopeCloseable)?.close()
    }

    /**
     * Remove all objects from the scope.
     */
    override fun clear() {
        val refs = synchronized(_lock) {
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
class SingleItemScopeRegistry<A> : ScopeRegistry<A> {
    private val _lock = Any()
    @Volatile private var _pair: Pair<ScopeRegistry.Key<A>, () -> Any?>? = null

    override fun getOrCreate(key: ScopeRegistry.Key<A>, creator: () -> Reference<Any>): Any {
        val (oldRef, value) = synchronizedIfNull(
                lock = _lock,
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

    override fun getOrNull(key: ScopeRegistry.Key<A>) = _pair?.let { (pKey, pRef) -> if (key == pKey) pRef else null }

    /**
     * @return Whether or not this scope is empty (contains no item).
     */
    fun isEmpty(): Boolean = _pair == null

    override fun values() = _pair?.let { listOf(it) } ?: emptyList()

    override fun remove(key: ScopeRegistry.Key<A>) {
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

enum class ScopeRepositoryType {
    MULTI_ITEM,
    SINGLE_ITEM
}

fun <A> newScopeRegistry(type: ScopeRepositoryType) = when (type) {
    ScopeRepositoryType.MULTI_ITEM -> MultiItemScopeRegistry<A>()
    ScopeRepositoryType.SINGLE_ITEM -> SingleItemScopeRegistry<A>()
}


/**
 * A scope is an object that can return (or create) a [ScopeRegistry] according to a context.
 *
 * @param EC The Environment Context: That's the context that is given to the scope from the retriever code.
 * @param BC The Binding Context: That's the context that is given by the scope to the bindings.
 *   It is often the same as [EC], in which case you should use [SimpleScope] instead.
 */
interface Scope<in EC, out BC, in A> {

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
    fun getRegistry(receiver: Any?, context: EC): ScopeRegistry<in A>
}

/**
 * Simple [Scope] where the Environment Context and the Binding Context do not differ.
 */
interface SimpleScope<C, in A> : Scope<C, C, A> {
    override fun getBindingContext(envContext: C) = envContext

    @Suppress("UNCHECKED_CAST")
    operator fun <T: C> invoke() = this as SimpleScope<T, A>

}

class BasicScope(val registry: ScopeRegistry<in Any?>) : SimpleScope<Any?, Any?> {
    override fun getRegistry(receiver: Any?, context: Any?) = registry
}

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
class NoScope: Scope<Any?, Nothing?, Any?> {

    override fun getBindingContext(envContext: Any?): Nothing? = null

    private val _registry by lazy { MultiItemScopeRegistry<Any?>() }

    override fun getRegistry(receiver: Any?, context: Any?) = _registry
}
