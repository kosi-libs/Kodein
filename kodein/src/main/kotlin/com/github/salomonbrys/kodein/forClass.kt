@file:Suppress("unused")

package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

/**
 * Retrives a currated provider from a factory whose argument is a [Class].
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForClass(kodein: Kodein, tag: Any? = null): () -> R = kodein.providerFromFactory(T::class.java as Class<*>, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForClassOrNull(kodein: Kodein, tag: Any? = null): (() -> R)? = kodein.providerFromFactoryOrNull(T::class.java as Class<*>, tag)

/**
 * Retrives an instance from a factory whose argument is a [Class].
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForClass(kodein: Kodein, tag: Any? = null): R = kodein.instanceFromFactory(T::class.java as Class<*>, tag)

/**
 * Retrives an instance from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForClassOrNull(kodein: Kodein, tag: Any? = null): R? = kodein.instanceFromFactoryOrNull(T::class.java as Class<*>, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [KClass].
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForKClass(kodein: Kodein, tag: Any? = null): () -> R = kodein.providerFromFactory(T::class as KClass<*>, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForKClassOrNull(kodein: Kodein, tag: Any? = null): (() -> R)? = kodein.providerFromFactoryOrNull(T::class as KClass<*>, tag)

/**
 * Retrives an instance from a factory whose argument is a [KClass].
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForKClass(kodein: Kodein, tag: Any? = null): R = kodein.instanceFromFactory(T::class as KClass<*>, tag)

/**
 * Retrives an instance from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param kodein The kodein object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForKClassOrNull(kodein: Kodein, tag: Any? = null): R? = kodein.instanceFromFactoryOrNull(T::class as KClass<*>, tag)



/**
 * Retrives a currated provider from a factory whose argument is a [Class].
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.providerForClass(tag: Any? = null): () -> R = providerForClass(kodein, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.providerForClassOrNull(tag: Any? = null): (() -> R)? = providerForClassOrNull(kodein, tag)

/**
 * Retrives an instance from a factory whose argument is a [Class].
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.instanceForClass(tag: Any? = null): R = instanceForClass(kodein, tag)

/**
 * Retrives an instance from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.instanceForClassOrNull(tag: Any? = null): R? = instanceForClassOrNull(kodein, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [KClass].
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.providerForKClass(tag: Any? = null): () -> R = providerForKClass(kodein, tag)

/**
 * Retrives a currated provider from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.providerForKClassOrNull(tag: Any? = null): (() -> R)? = providerForKClassOrNull(kodein, tag)

/**
 * Retrives an instance from a factory whose argument is a [KClass].
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.instanceForKClass(tag: Any? = null): R = instanceForKClass(kodein, tag)

/**
 * Retrives an instance from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinAware, reified R : Any> T.instanceForKClassOrNull(tag: Any? = null): R? = instanceForKClassOrNull(kodein, tag)



/**
 * Retrives a lazy currated provider from a factory whose argument is a [Class].
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForClass(injector: KodeinInjector, tag: Any? = null): Lazy<() -> R> = injector.providerFromFactory(T::class.java as Class<*>, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a provider of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<(() -> R)?> = injector.providerFromFactoryOrNull(T::class.java as Class<*>, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [Class].
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForClass(injector: KodeinInjector, tag: Any? = null): Lazy<R> = injector.instanceFromFactory(T::class.java as Class<*>, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either an instance of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<R?> = injector.instanceFromFactoryOrNull(T::class.java as Class<*>, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [KClass].
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForKClass(injector: KodeinInjector, tag: Any? = null): Lazy<() -> R> = injector.providerFromFactory(T::class as KClass<*>, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a provider of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.providerForKClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<(() -> R)?> = injector.providerFromFactoryOrNull(T::class as KClass<*>, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [KClass].
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForKClass(injector: KodeinInjector, tag: Any? = null): Lazy<R> = injector.instanceFromFactory(T::class as KClass<*>, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param injector The injector object to use for retrieval.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either an instance of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any, reified R : Any> T.instanceForKClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<R?> = injector.instanceFromFactoryOrNull(T::class as KClass<*>, tag)



/**
 * Retrives a lazy currated provider from a factory whose argument is a [Class].
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.providerForClass(tag: Any? = null): Lazy<() -> R> = providerForClass(injector, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The provider will give the factory the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a provider of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.providerForClassOrNull(tag: Any? = null): Lazy<(() -> R)?> = providerForClassOrNull(injector, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [Class].
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForClass(tag: Any? = null): Lazy<R> = instanceForClass(injector, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [Class], or null if no factory is found.
 *
 * The factory will be given the [Class] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either an instance of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForClassOrNull(tag: Any? = null): Lazy<R?> = instanceForClassOrNull(injector, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [KClass].
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.providerForKClass(tag: Any? = null): Lazy<() -> R> = providerForKClass(injector, tag)

/**
 * Retrives a lazy currated provider from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The provider will give the factory the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a provider of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.providerForKClassOrNull(tag: Any? = null): Lazy<(() -> R)?> = providerForKClassOrNull(injector, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [KClass].
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T].
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForKClass(tag: Any? = null): Lazy<R> = instanceForKClass(injector, tag)

/**
 * Retrives a lazy instance from a factory whose argument is a [KClass], or null if no factory is found.
 *
 * The factory will be given the [KClass] of the receiver as argument.
 *
 * @param T The type of the receiver, will be the type provided to the factory.
 * @param R The bound type of the object to retrieve with the returned provider.
 * @receiver The object whose class is used.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either an instance of [T] or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForKClassOrNull(tag: Any? = null): Lazy<R?> = instanceForKClassOrNull(injector, tag)
