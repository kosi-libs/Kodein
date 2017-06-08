package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.bindings.*

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T: Any> Kodein.Builder.factory(noinline creator: BindingKodein.(A) -> T) = FactoryBinding<A, T>(generic(), generic(), creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be kept.
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.provider(noinline creator: NoArgBindingKodein.() -> T) = ProviderBinding(generic(), creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.singleton(noinline creator: NoArgBindingKodein.() -> T) = SingletonBinding(generic(), creator)

/**
 * Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
inline fun <reified A, reified T: Any> Kodein.Builder.multiton(noinline creator: BindingKodein.(A) -> T) = MultitonBinding<A, T>(generic(), generic(), creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.eagerSingleton(noinline creator: NoArgBindingKodein.() -> T) = EagerSingletonBinding(this, generic(), creator)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be kept.
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.instance(instance: T) = InstanceBinding(generic(), instance)

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
inline fun <reified C, reified T: Any> Kodein.Builder.scopedSingleton(scope: Scope<C>, noinline creator: Kodein.(C) -> T)
    = ScopedSingletonBinding(generic(), generic(), scope, creator)

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
inline fun <C, reified T: Any> Kodein.Builder.autoScopedSingleton(scope: AutoScope<C>, noinline creator: Kodein.(C) -> T)
    = AutoScopedSingletonBinding(generic(), scope, creator)

/**
 * Creates a referenced singleton, will return always the same object as long as the reference is valid.
 *
 * T generics will be kept.
 *
 * @param T The singleton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.
 */
inline fun <reified T: Any> Kodein.Builder.refSingleton(refMaker: RefMaker, noinline creator: NoArgBindingKodein.() -> T)
    = RefSingletonBinding(generic(), refMaker, creator)

/**
 * Creates a referenced multiton, for the same argument, will return always the same object as long as the reference is valid.
 *
 * A & T generics will be kept.
 *
 * @param T The multiton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the multiton object. For the same argument, will be called only if the multiton does not already exist or if the reference is not valid anymore.
 */
inline fun <reified A, reified T: Any> Kodein.Builder.refMultiton(refMaker: RefMaker, noinline creator: BindingKodein.(A) -> T)
    = RefMultitonBinding(generic(), generic(), refMaker, creator)
