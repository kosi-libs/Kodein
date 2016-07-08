@file:Suppress("unused")

package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

/**
 * Allows to get a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : Any> T.withClass(kodein: Kodein): CurriedKodeinFactory<Class<*>> = CurriedKodeinFactory(kodein, T::class.java, typeToken())

/**
 * Allows to get a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : Any> T.withKClass(kodein: Kodein): CurriedKodeinFactory<KClass<*>> = CurriedKodeinFactory(kodein, T::class, typeToken())

/**
 * Allows to get a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : KodeinAware> T.withClass(): CurriedKodeinFactory<Class<*>> = CurriedKodeinFactory(kodein, T::class.java, typeToken())

/**
 * Allows to get a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified T : KodeinAware> T.withKClass(): CurriedKodeinFactory<KClass<*>> = CurriedKodeinFactory(kodein, T::class, typeToken())



/**
 * Allows to inject a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for injection.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> T.withClass(injector: KodeinInjector): CurriedInjectorFactory<Class<*>> = CurriedInjectorFactory(injector, T::class.java, typeToken())

/**
 * Allows to inject a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for injection.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> T.withKClass(injector: KodeinInjector): CurriedInjectorFactory<KClass<*>> = CurriedInjectorFactory(injector, T::class, typeToken())

/**
 * Allows to inject a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : KodeinInjected> T.withClass(): CurriedInjectorFactory<Class<*>> = CurriedInjectorFactory(injector, T::class.java, typeToken())

/**
 * Allows to inject a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : KodeinInjected> T.withKClass(): CurriedInjectorFactory<KClass<*>> = CurriedInjectorFactory(injector, T::class, typeToken())



/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param lazyKodein The lazy Kodein object to use for injection.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> T.withClass(lazyKodein: LazyKodein): CurriedLazyKodeinFactory<Class<*>> = CurriedLazyKodeinFactory(lazyKodein, T::class.java, typeToken())

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @param lazyKodein The lazy Kodein object to use for injection.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : Any> T.withKClass(lazyKodein: LazyKodein): CurriedLazyKodeinFactory<KClass<*>> = CurriedLazyKodeinFactory(lazyKodein, T::class, typeToken())

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a [Class] argument.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : LazyKodeinAware> T.withClass(): CurriedLazyKodeinFactory<Class<*>> = CurriedLazyKodeinFactory(kodein, T::class.java, typeToken())

/**
 * Allows to lazily retrive a provider or an instance from a curried factory with a [KClass] argument.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the class provided to the factory.
 * @receiver The object whose class is used.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified T : LazyKodeinAware> T.withKClass(): CurriedLazyKodeinFactory<KClass<*>> = CurriedLazyKodeinFactory(kodein, T::class, typeToken())
