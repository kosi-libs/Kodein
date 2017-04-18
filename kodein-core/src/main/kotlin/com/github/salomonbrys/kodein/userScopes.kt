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
     * Get a registry for a given context. Should always return the same registry for the same context.
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
        private val _creator: ProviderKodein.(C) -> T
) {

    /**
     * Finds an instance inside a scope, or creates it if needs be.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @param arg: The argument to use to get the instance.
     */
    @Suppress("UNCHECKED_CAST")
    protected fun getScopedInstance(kodein: ProviderKodein, key: Kodein.Key, arg: A): T {
        val (context, registry) = getContextAndRegistry(arg)
        return registry.getOrCreate(key.bind) { _creator(kodein, context) }
    }

    /**
     * Retrieve the scope context and registry associated with the given argument.
     *
     * @param arg The argument associated with the returned scope.
     * @return The scope associated with the given argument.
     */
    abstract protected fun getContextAndRegistry(arg: A): Pair<C, ScopeRegistry>
}


/**
 * Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @property contextType The scope context type.
 * @property createdType The singleton type.
 * @property _scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
class CScopedSingleton<C, out T : Any>(val contextType: Type, override val createdType: Type, private val _scope: Scope<C>, creator: ProviderKodein.(C) -> T)
: AScoped<C, C, T>(creator), Factory<C, T>
{
    override fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: C) = getScopedInstance(ProviderKodeinImpl(kodein), key, arg)

    override val argType: Type get() = contextType

    override fun getContextAndRegistry(arg: C): Pair<C, ScopeRegistry> = arg to _scope.getRegistry(arg)

    override fun factoryName() = "scopedSingleton(${_scope.javaClass.simpleDispString})"
    override fun factoryFullName() = "scopedSingleton(${_scope.javaClass.fullDispString})"
}

/**
 * Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * C & T generics will be kept.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <reified C, reified T : Any> Kodein.Builder.genericScopedSingleton(scope: Scope<C>, noinline creator: ProviderKodein.(C) -> T)
        = CScopedSingleton(genericToken<C>().type, genericToken<T>().type, scope, creator)

/**
 * Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * C & T generics will be erased!
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <reified C, reified T : Any> Kodein.Builder.erasedScopedSingleton(scope: Scope<C>, noinline creator: ProviderKodein.(C) -> T)
        = CScopedSingleton(typeClass<C>(), T::class.java, scope, creator)



/**
 * Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param createdType The singleton type.
 * @param _scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
class CAutoScopedSingleton<out C, out T : Any>(override val createdType: Type, private val _scope: AutoScope<C>, creator: ProviderKodein.(C) -> T)
: AScoped<Unit, C, T>(creator), Provider<T>
{
    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key) = getScopedInstance(kodein, key, Unit)

    override val argType: Type get() = Unit::class.java

    override fun getContextAndRegistry(arg: Unit): Pair<C, ScopeRegistry> = _scope.getContext().let { it to _scope.getRegistry(it) }

    override fun factoryName() = "autoScopedSingleton(${_scope.javaClass.simpleDispString})"
    override fun factoryFullName() = "autoScopedSingleton(${_scope.javaClass.fullDispString})"
}

/**
 * Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * T generics will be kept.
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <C, reified T : Any> Kodein.Builder.genericAutoScopedSingleton(scope: AutoScope<C>, noinline creator: ProviderKodein.(C) -> T)
        = CAutoScopedSingleton(genericToken<T>().type, scope, creator)

/**
 * Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * T generics will be erased!
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <C, reified T : Any> Kodein.Builder.erasedAutoScopedSingleton(scope: AutoScope<C>, noinline creator: ProviderKodein.(C) -> T)
        = CAutoScopedSingleton(T::class.java, scope, creator)
