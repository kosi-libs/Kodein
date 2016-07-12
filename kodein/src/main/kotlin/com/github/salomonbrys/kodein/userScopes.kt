package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*


/**
 * Represents a scope: used to store and retrieve scoped singleton objects.
 */
class ScopeRegistry {

    /**
     * Map of stored objects
     */
    private val _cache = HashMap<Kodein.Bind, Any>()

    /**
     * Either get a singleton object if it exists in this scope, or create it if it does not.
     *
     * @param T The type of the singleton object to get / create.
     * @param bind The type and tag of the singleton object to get / create.
     * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getOrCreate(bind: Kodein.Bind, creator: () -> T): T {
        return _cache[bind] as T? ?: synchronized(_cache) {
            _cache.getOrPut(bind) { creator() } as T
        }
    }

    /**
     * @return The singleton object for the given type and tag in this scope, or null if there was none.
     */
    operator fun get(bind: Kodein.Bind): Any? = synchronized(_cache) { _cache[bind] }

    /**
     * @return Whether or not there is a singleton object for the given type and tag in this scope.
     */
    operator fun contains(bind: Kodein.Bind): Boolean = synchronized(_cache) { bind in _cache }

    /**
     * Remove all objects from the scope.
     */
    fun clear(): Unit = synchronized(_cache) { _cache.clear() }

    /**
     * @return A **copy** of the storage of this scope.
     */
    fun objects(): HashMap<Kodein.Bind, Any> = synchronized(_cache) { HashMap(_cache) }

    /**
     * Remove a singleton object from this scope, if it exist.
     *
     * @param bind The type and tag of the singleton object to remove.
     * @return The removed object, if it was found in the scope.
     */
    fun remove(bind: Kodein.Bind): Any? = synchronized(_cache) { _cache.remove(bind) }

    /**
     * The number of singleton objects currently created in this scope.
     */
    val size: Int get() = synchronized(_cache) { _cache.size }

    /**
     * @return Whether or not this scope is empty (contains no singleton objects).
     */
    fun isEmpty(): Boolean = synchronized(_cache) { _cache.isEmpty() }
}

/**
 * An object capable of providing a [ScopeRegistry] for a given `C` context.
 *
 * @param C The type of the context that will be used to retrieve the registry.
 */
interface Scope<in C> {

    /**
     * Get a registry for a given context.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    fun getRegistry(context: C): ScopeRegistry
}

/**
 * An object that can, in addition to being a regular scope, can also get a context from a static environment.
 *
 * @param C The type of the context that can be statically retrieved.
 */
interface AutoScope<C> : Scope<C> {
    /**
     * Get the context according to the static environment.
     *
     * @return The context to use to retrieve a registry with [Scope.getRegistry].
     */
    fun getContext() : C
}

/**
 * A factory to bind a type and tag into a [Scope] or an [AutoScope].
 *
 * @param A The type of argument that is needed to get a `C` context.
 * @param C The type of context that will be used to get a [ScopeRegistry].
 * @param T The singleton type.
 * @param _creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
abstract class AScoped<in A, out C, out T : Any>(
        override val argType: Type,
        override val createdType: Type,
        override val factoryName: String,
        private val _creator: Kodein.(C) -> T
) : Factory<A, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getInstance(kodein: Kodein, key: Kodein.Key, arg: A): T {
        val (context, registry) = _getContextAndRegistry(arg)
        return registry.getOrCreate(key.bind) { _creator(kodein, context) }
    }

    /**
     * Retrieve the scope context and registry associated with the given argument.
     *
     * @param arg The argument associated with the returned scope.
     * @return The scope associated with the given argument.
     */
    abstract protected fun _getContextAndRegistry(arg: A): Pair<C, ScopeRegistry>
}


/**
 * Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param contextType The scope context type.
 * @param createdType The singleton type.
 * @param _scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
class CScopedSingleton<C, out T : Any>(contextType: Type, createdType: Type, private val _scope: Scope<C>, creator: Kodein.(C) -> T)
: AScoped<C, C, T>(contextType, createdType, "scopedSingleton", creator)
{
    override fun _getContextAndRegistry(arg: C): Pair<C, ScopeRegistry> = arg to _scope.getRegistry(arg)

    override val description: String get() = "$factoryName(${_scope.javaClass.simpleDispString}) { ${argType.simpleDispString} -> ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName(${_scope.javaClass.fullDispString}) { ${argType.fullDispString} -> ${createdType.fullDispString} } "
}

/**
 * Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which thie singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <reified C, reified T : Any> Kodein.Builder.scopedSingleton(scope: Scope<C>, noinline creator: Kodein.(C) -> T)
        = CScopedSingleton(typeToken<C>().type, typeToken<T>().type, scope, creator)



/**
 * Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param createdType The singleton type.
 * @param _scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
class CAutoScopedSingleton<out C, out T : Any>(createdType: Type, private val _scope: AutoScope<C>, creator: Kodein.(C) -> T)
: AScoped<Unit, C, T>(Unit::class.java, createdType, "autoScopedSingleton", creator)
{
    override fun _getContextAndRegistry(arg: Unit): Pair<C, ScopeRegistry> = _scope.getContext().let { it to _scope.getRegistry(it) }

    override val description: String get() = "$factoryName(${_scope.javaClass.simpleDispString}) { ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName(${_scope.javaClass.fullDispString}) { ${createdType.fullDispString} } "
}

/**
 * Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <C, reified T : Any> Kodein.Builder.autoScopedSingleton(scope: AutoScope<C>, noinline creator: Kodein.(C) -> T)
        = CAutoScopedSingleton(typeToken<T>().type, scope, creator)

