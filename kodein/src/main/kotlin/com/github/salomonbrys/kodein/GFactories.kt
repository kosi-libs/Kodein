package com.github.salomonbrys.kodein

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
inline fun <reified A, reified T : Any> Kodein.Builder.factory(noinline creator: FactoryKodein.(A) -> T) = genericFactory(creator)

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
inline fun <reified T : Any> Kodein.Builder.provider(noinline creator: ProviderKodein.() -> T) = genericProvider(creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.singleton(noinline creator: ProviderKodein.() -> T) = genericSingleton(creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.eagerSingleton(noinline creator: ProviderKodein.() -> T) = genericEagerSingleton(creator)

/**
 * Creates a thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 * @return A thread singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.threadSingleton(noinline creator: ProviderKodein.() -> T) = genericThreadSingleton(creator)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be kept.
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.instance(instance: T) = genericInstance(instance)
