@file:Suppress("unused")

package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

/**
 * Allows to get a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The Kodein object to use for retrieval.
 * @param of The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : Any> Kodein.withClassOf(@Suppress("UNUSED_PARAMETER") of: T): CurriedKodeinFactory<Class<*>> = with(T::class.java)

/**
 * Allows to get a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The Kodein object to use for retrieval.
 * @param of The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : Any> Kodein.withKClassOf(@Suppress("UNUSED_PARAMETER") of: T): CurriedKodeinFactory<KClass<*>> = with(T::class)

/**
 * Allows to get a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : KodeinAware> T.withClass(): CurriedKodeinFactory<Class<*>> = with(T::class.java)

/**
 * Allows to get a provider or an instance from a curried factory with a `KClass` argument.
 *
 * The provider will give the factory the `KClass` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : KodeinAware> T.withKClass(): CurriedKodeinFactory<KClass<*>> = with(T::class)



/**
 * Allows to inject a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The Injector object to use for retrieval.
 * @param of The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> KodeinInjector.withClassOf(@Suppress("UNUSED_PARAMETER") of: T): CurriedInjectorFactory<Class<*>> = with(T::class.java)

/**
 * Allows to inject a provider or an instance from a curried factory with a `KClass` argument.
 *
 * The provider will give the factory the `KClass` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The Injector object to use for retrieval.
 * @param of The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> KodeinInjector.withKClassOf(@Suppress("UNUSED_PARAMETER") of: T): CurriedInjectorFactory<KClass<*>> = with(T::class)

/**
 * Allows to inject a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : KodeinInjected> T.withClass(): CurriedInjectorFactory<Class<*>> = with(T::class.java)

/**
 * Allows to inject a provider or an instance from a curried factory with a `KClass` argument.
 *
 * The provider will give the factory the `KClass` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : KodeinInjected> T.withKClass(): CurriedInjectorFactory<KClass<*>> = with(T::class)



/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The lazy Kodein object to use for injection.
 * @param of The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> LazyKodein.withClass(@Suppress("UNUSED_PARAMETER") of: T): CurriedLazyKodeinFactory<Class<*>> = with(T::class.java)

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a `KClass` argument.
 *
 * The provider will give the factory the `KClass` of the parameter as argument.
 *
 * @param T The type of the parameter, will be the class provided to the factory.
 * @receiver The lazy Kodein object to use for injection.
 * @param of The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> LazyKodein.withKClass(@Suppress("UNUSED_PARAMETER") of: T): CurriedLazyKodeinFactory<KClass<*>> = with(T::class)

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a `Class` argument.
 *
 * The provider will give the factory the `Class` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : LazyKodeinAware> T.withClass(): CurriedLazyKodeinFactory<Class<*>> = with(T::class.java)

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a `KClass` argument.
 *
 * The provider will give the factory the `KClass` of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : LazyKodeinAware> T.withKClass(): CurriedLazyKodeinFactory<KClass<*>> = with(T::class)
