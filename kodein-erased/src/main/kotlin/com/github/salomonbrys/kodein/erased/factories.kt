package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.*

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.factory(noinline creator: Kodein.(A) -> T) = erasedFactory(creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.provider(noinline creator: Kodein.() -> T) = erasedProvider(creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.singleton(noinline creator: Kodein.() -> T) = erasedSingleton(creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.eagerSingleton(noinline creator: Kodein.() -> T) = erasedEagerSingleton(creator)

/**
 * Creates a thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 * @return A thread singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.threadSingleton(noinline creator: Kodein.() -> T) = erasedThreadSingleton(creator)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.instance(instance: T) = erasedInstance(instance)
