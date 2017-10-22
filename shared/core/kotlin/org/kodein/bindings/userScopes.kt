//package org.kodein.bindings
//
//import org.kodein.*
//import org.kodein.internal.synchronizedIfNull
//
//
///**
// * Represents a scope: used to store and retrieve scoped singleton objects.
// */
//@Suppress("UNCHECKED_CAST")
//class SingletonScopeRegistry {
//
//    /**
//     * Map of stored objects
//     */
//    private val _cache = newConcurrentMap<Kodein.Bind<*>, Any>()
//
//    private val _writeLock = Any()
//
//    /**
//     * Either get a singleton object if it exists in this scope, or create it if it does not.
//     *
//     * @param T The type of the singleton object to get / create.
//     * @param bind The type and tag of the singleton object to get / create.
//     * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
//     */
//    @Suppress("UNCHECKED_CAST")
//    fun <T : Any> getOrCreate(bind: Kodein.Bind<T>, creator: () -> T): T {
//        return synchronizedIfNull(
//                lock = _writeLock,
//                predicate = { _cache[bind] as T? },
//                ifNotNull = { it },
//                ifNull = { creator().also { _cache[bind] == it } }
//        )
//    }
//
//    /**
//     * @return The singleton object for the given type and tag in this scope, or null if there was none.
//     */
//    operator fun <T: Any> get(bind: Kodein.Bind<T>): T? = _cache[bind] as T?
//
//    /**
//     * @return Whether or not there is a singleton object for the given type and tag in this scope.
//     */
//    operator fun contains(bind: Kodein.Bind<*>): Boolean = bind in _cache
//
//    /**
//     * Remove all objects from the scope.
//     */
//    fun clear(): Unit = _cache.clear()
//
//    /**
//     * @return A **copy** of the storage of this scope.
//     */
//    fun objects(): HashMap<Kodein.Bind<*>, Any> = HashMap(_cache)
//
//    /**
//     * Remove a singleton object from this scope, if it exist.
//     *
//     * @param bind The type and tag of the singleton object to remove.
//     * @return The removed object, if it was found in the scope.
//     */
//    fun <T: Any> remove(bind: Kodein.Bind<T>): T? = _cache.remove(bind) as T?
//
//    /**
//     * The number of singleton objects currently created in this scope.
//     */
//    val size: Int get() = _cache.size
//
//    /**
//     * @return Whether or not this scope is empty (contains no singleton objects).
//     */
//    fun isEmpty(): Boolean = _cache.isEmpty()
//}
//
///**
// * An object capable of providing a [ScopeRegistry] for a given `C` context.
// *
// * @param C The type of the context that will be used to retrieve the registry.
// */
//interface SingletonScope<in C> {
//
//    /**
//     * Get a registry for a given context. Should always return the same registry for the same context.
//     *
//     * @param context The context associated with the returned registry.
//     * @return The registry associated with the given context.
//     */
//    fun getRegistry(context: C): SingletonScopeRegistry
//}
//
///**
// * An object that can, in addition to being a regular scope, can also get a context from a static environment.
// *
// * @param C The type of the context that can be statically retrieved.
// */
//interface AutoSingletonScope<C> : SingletonScope<C> {
//    /**
//     * Get the context according to the static environment.
//     *
//     * @return The context to use to retrieve a registry with [Scope.getRegistry].
//     */
//    fun getContext() : C
//}
//
///**
// * A factory to bind a type and tag into a [Scope] or an [AutoScope].
// *
// * @param A The type of argument that is needed to get a `C` context.
// * @param C The type of context that will be used to get a [ScopeRegistry].
// * @param T The singleton type.
// * @param _creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
// */
//abstract class AScopedBinding<in A, out C, T : Any> internal constructor(
//        private val _creator: NoArgSimpleBindingKodein<T>.(C) -> T
//) {
//
//    /**
//     * Finds an instance inside a scope, or creates it if needs be.
//     *
//     * @param kodein: A Kodein instance to use for transitive dependencies.
//     * @param key: The key of the instance to get.
//     * @param arg: The argument to use to get the instance.
//     */
//    @Suppress("UNCHECKED_CAST")
//    protected fun getScopedFactory(kodein: NoArgBindingKodein<T>, key: Kodein.Key<A, T>): (A) -> T {
//        return { arg ->
//            val (context, registry) = getContextAndRegistry(arg)
//            registry.getOrCreate(key.bind) { _creator(kodein, context) }
//        }
//    }
//
//    /**
//     * Retrieve the scope context and registry associated with the given argument.
//     *
//     * @param arg The argument associated with the returned scope.
//     * @return The scope associated with the given argument.
//     */
//    abstract protected fun getContextAndRegistry(arg: A): Pair<C, SingletonScopeRegistry>
//}
//
//internal class NoArgScopedBindingKodeinWrap<A, out T: Any>(private val _kodein: BindingKodein<A, T>, private val _overrideKey: Kodein.Key<A, T>) : NoArgBindingKodein<T>, Kodein by _kodein {
//    override val bind get() = _kodein.key.bind
//    override val receiver get() = _kodein.receiver
//    override fun overriddenProvider(): () -> T = _kodein.overriddenFactory().toProvider { Unit }
//    override fun overriddenProviderOrNull(): (() -> T)? = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
//    override fun overriddenInstance(): T = overriddenProvider().invoke()
//    override fun overriddenInstanceOrNull(): T? = overriddenProviderOrNull()?.invoke()
//}
//
//
///**
// * Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.
// *
// * @param C The scope context type.
// * @param T The singleton type.
// * @property contextType The scope context type.
// * @property createdType The singleton type.
// * @property _scope The scope object in which the singleton will be stored.
// * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
// */
//class ScopedSingletonBinding<C, T : Any>(val contextType: TypeToken<C>, override val createdType: TypeToken<out T>, private val _scope: SingletonScope<C>, creator: NoArgSimpleBindingKodein<T>.(C) -> T)
//: AScopedBinding<C, C, T>(creator), KodeinBinding<C, T>
//{
//    @Suppress("UNCHECKED_CAST")
//    override fun getFactory(kodein: BindingKodein<C, T>): (C) -> T = getScopedFactory(NoArgBindingKodeinWrap(kodein), kodein.key)
//
//    override val argType = contextType
//
//    override fun getContextAndRegistry(arg: C): Pair<C, SingletonScopeRegistry> = arg to _scope.getRegistry(arg)
//
//    override fun factoryName() = "scopedSingleton(${TTOf(_scope).simpleDispString ()})"
//    override fun factoryFullName() = "scopedSingleton(${TTOf(_scope).fullDispString()})"
//}
//
///**
// * Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.
// *
// * @param C The scope context type.
// * @param T The singleton type.
// * @param createdType The singleton type.
// * @param _scope The scope object in which the singleton will be stored.
// * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
// */
//class AutoScopedSingletonBinding<out C, T : Any>(override val createdType: TypeToken<out T>, private val _scope: AutoSingletonScope<C>, creator: NoArgSimpleBindingKodein<T>.(C) -> T)
//: AScopedBinding<Unit, C, T>(creator), NoArgKodeinBinding<T>
//{
//    override fun getProvider(kodein: NoArgBindingKodein<T>): () -> T = getScopedFactory(kodein, Kodein.Key(kodein.bind, UnitToken)).toProvider { Unit }
//
//    override val argType get() = UnitToken
//
//    override fun getContextAndRegistry(arg: Unit): Pair<C, SingletonScopeRegistry> = _scope.getContext().let { it to _scope.getRegistry(it) }
//
//    override fun factoryName() = "autoScopedSingleton(${TTOf(_scope).simpleDispString()})"
//    override fun factoryFullName() = "autoScopedSingleton(${TTOf(_scope).fullDispString()})"
//}
