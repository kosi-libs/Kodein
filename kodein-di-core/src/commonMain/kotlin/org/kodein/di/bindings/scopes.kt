package org.kodein.di.bindings

import org.kodein.di.Volatile
import org.kodein.di.internal.*

interface ScopeCloseable {
    fun close()
}

/**
 * A registry is responsible managing references inside a scope.
 */
sealed class ScopeRegistry<A> : ScopeCloseable {
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
    abstract fun getOrCreate(key: ScopeRegistry.Key<A>, sync: Boolean = true, creator: () -> Reference<Any>): Any

    abstract fun getOrNull(key: ScopeRegistry.Key<A>): (() -> Any?)?

    abstract fun values(): Iterable<Pair<ScopeRegistry.Key<A>, () -> Any?>>

    abstract fun remove(key: ScopeRegistry.Key<A>)

    abstract fun clear()

    final override fun close() = clear()
}

/**
 * Standard [ScopeRegistry] implementation.
 */
class MultiItemScopeRegistry<A> : ScopeRegistry<A>() {

    private val _cache = newConcurrentMap<ScopeRegistry.Key<A>, () -> Any?>()

    private val _lock = Any()

    override fun getOrCreate(key: ScopeRegistry.Key<A>, sync: Boolean, creator: () -> Reference<Any>): Any {
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

    override fun getOrNull(key: ScopeRegistry.Key<A>) = _cache[key]

    override fun values() = _cache.map { it.toPair() }

    override fun remove(key: ScopeRegistry.Key<A>) {
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
class SingleItemScopeRegistry<A> : ScopeRegistry<A>() {
    private val _lock = Any()
    @Volatile private var _pair: Pair<ScopeRegistry.Key<A>, () -> Any?>? = null

    override fun getOrCreate(key: ScopeRegistry.Key<A>, sync: Boolean, creator: () -> Reference<Any>): Any {
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

@Deprecated("Use directly MultiItemScopeRegistry or SingleItemScopeRegistry constructors")
enum class ScopeRepositoryType {
    MULTI_ITEM,
    SINGLE_ITEM
}

@Suppress("DEPRECATION", "DeprecatedCallableAddReplaceWith")
@Deprecated("Use directly MultiItemScopeRegistry or SingleItemScopeRegistry constructors")
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

/**
 * [Scope] that is not bound to a context (always lives).
 *
 * This is kind of equivalent to having no scope at all, except that you can call [clear].
 */
class UnboundedScope(val registry: ScopeRegistry<in Any?> = MultiItemScopeRegistry()) : SimpleScope<Any?, Any?>, ScopeCloseable {
    override fun getRegistry(receiver: Any?, context: Any?) = registry

    override fun close() = registry.clear()
}

// Deprecated since 5.4.0
@Deprecated("BasicScope has been renamed UnboundedScope", ReplaceWith("UnboundedScope"))
typealias BasicScope = UnboundedScope

abstract class SubScope<in EC, BC, in A>(val parentScope: Scope<BC, Any?, Any?>) : Scope<EC, BC, A> {

    private data class Key<C>(override val arg: C) : ScopeRegistry.Key<Any?>

    override fun getRegistry(receiver: Any?, context: EC): ScopeRegistry<in A> {
        val bindingContext = getBindingContext(context)
        val parentRegistry = parentScope.getRegistry(receiver, bindingContext)
        @Suppress("UNCHECKED_CAST")
        return parentRegistry.getOrCreate(Key(context), false) { SingletonReference.make { newRegistry() } } as ScopeRegistry<in A>
    }

    fun removeFromParent(receiver: Any?, context: EC) {
        val bindingContext = getBindingContext(context)
        val parentRegistry = parentScope.getRegistry(receiver, bindingContext)
        parentRegistry.remove(Key(context))
    }

    open fun newRegistry(): ScopeRegistry<in A> = MultiItemScopeRegistry()
}

open class SimpleSubScope<C, in A>(parentScope: Scope<C, Any?, Any?>) : SimpleScope<C, A>, SubScope<C, C, A>(parentScope)

/**
 * Default [Scope]: will always return the same registry, no matter the context.
 */
class NoScope: Scope<Any?, Nothing?, Any?> {

    override fun getBindingContext(envContext: Any?): Nothing? = null

    private val _registry = MultiItemScopeRegistry<Any?>()

    override fun getRegistry(receiver: Any?, context: Any?) = _registry
}
