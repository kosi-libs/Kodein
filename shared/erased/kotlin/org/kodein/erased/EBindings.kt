@file:Suppress("unused")

package org.kodein.erased

import org.kodein.*
import org.kodein.bindings.*

inline fun <reified C> Kodein.Builder.scoped(scope: Scope<C>) = Kodein.BindBuilder.Scoped.Impl(erased(), scope)
inline fun <reified C> Kodein.Builder.contexted() = Kodein.BindBuilder.Scoped.Impl(erased(), scope)


/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T eraseds will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T: Any> Kodein.Builder.factory(noinline creator: BindingKodein.(A) -> T) = Factory<Unit, A, T>(AnyToken, erased(), erased(), creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T eraseds will be kept.
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.provider(noinline creator: NoArgBindingKodein.() -> T) = Provider(AnyToken, erased(), creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T eraseds will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <C, reified T: Any> Kodein.BindBuilder.Scoped<C>.singleton(ref: RefMaker? = null, noinline creator: NoArgScopedBindingKodein<C>.() -> T) = Singleton<C, T>(scope, contextType, erased(), ref, creator)

/**
 * Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.
 *
 * A & T eraseds will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
inline fun <C, reified A, reified T: Any> Kodein.BindBuilder.Scoped<C>.multiton(ref: RefMaker? = null, noinline creator: ScopedBindingKodein<C>.(A) -> T) = Multiton<C, A, T>(scope, contextType, erased(), erased(), ref, creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T eraseds will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.eagerSingleton(noinline creator: NoArgSimpleBindingKodein.() -> T) = EagerSingleton(this, erased(), creator)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T eraseds will be kept.
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T: Any> Kodein.Builder.instance(instance: T) = InstanceBinding(erased(), instance)
