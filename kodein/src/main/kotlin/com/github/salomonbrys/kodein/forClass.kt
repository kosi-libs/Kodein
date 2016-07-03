@file:Suppress("unused")

package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

inline fun <reified T : Any, reified R : Any> T.providerForClass(kodein: Kodein, tag: Any? = null): () -> R = kodein.providerFromFactory(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForClassOrNull(kodein: Kodein, tag: Any? = null): (() -> R)? = kodein.providerFromFactoryOrNull(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForClass(kodein: Kodein, tag: Any? = null): R = kodein.instanceFromFactory(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForClassOrNull(kodein: Kodein, tag: Any? = null): R? = kodein.instanceFromFactoryOrNull(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForKClass(kodein: Kodein, tag: Any? = null): () -> R = kodein.providerFromFactory(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForKClassOrNull(kodein: Kodein, tag: Any? = null): (() -> R)? = kodein.providerFromFactoryOrNull(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForKClass(kodein: Kodein, tag: Any? = null): R = kodein.instanceFromFactory(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForKClassOrNull(kodein: Kodein, tag: Any? = null): R? = kodein.instanceFromFactoryOrNull(T::class as KClass<*>, tag)



inline fun <reified T : KodeinAware, reified R : Any> T.providerForClass(tag: Any? = null): () -> R = providerForClass(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.providerForClassOrNull(tag: Any? = null): (() -> R)? = providerForClassOrNull(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.instanceForClass(tag: Any? = null): R = instanceForClass(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.instanceForClassOrNull(tag: Any? = null): R? = instanceForClassOrNull(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.providerForKClass(tag: Any? = null): () -> R = providerForKClass(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.providerForKClassOrNull(tag: Any? = null): (() -> R)? = providerForKClassOrNull(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.instanceForKClass(tag: Any? = null): R = instanceForKClass(kodein, tag)

inline fun <reified T : KodeinAware, reified R : Any> T.instanceForKClassOrNull(tag: Any? = null): R? = instanceForKClassOrNull(kodein, tag)



inline fun <reified T : Any, reified R : Any> T.providerForClass(injector: KodeinInjector, tag: Any? = null): Lazy<() -> R> = injector.providerFromFactory(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<(() -> R)?> = injector.providerFromFactoryOrNull(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForClass(injector: KodeinInjector, tag: Any? = null): Lazy<R> = injector.instanceFromFactory(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<R?> = injector.instanceFromFactoryOrNull(T::class.java as Class<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForKClass(injector: KodeinInjector, tag: Any? = null): Lazy<() -> R> = injector.providerFromFactory(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.providerForKClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<(() -> R)?> = injector.providerFromFactoryOrNull(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForKClass(injector: KodeinInjector, tag: Any? = null): Lazy<R> = injector.instanceFromFactory(T::class as KClass<*>, tag)

inline fun <reified T : Any, reified R : Any> T.instanceForKClassOrNull(injector: KodeinInjector, tag: Any? = null): Lazy<R?> = injector.instanceFromFactoryOrNull(T::class as KClass<*>, tag)



inline fun <reified T : KodeinInjected, reified R : Any> T.providerForClass(tag: Any? = null): Lazy<() -> R> = providerForClass(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.providerForClassOrNull(tag: Any? = null): Lazy<(() -> R)?> = providerForClassOrNull(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForClass(tag: Any? = null): Lazy<R> = instanceForClass(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForClassOrNull(tag: Any? = null): Lazy<R?> = instanceForClassOrNull(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.providerForKClass(tag: Any? = null): Lazy<() -> R> = providerForKClass(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.providerForKClassOrNull(tag: Any? = null): Lazy<(() -> R)?> = providerForKClassOrNull(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForKClass(tag: Any? = null): Lazy<R> = instanceForKClass(injector, tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForKClassOrNull(tag: Any? = null): Lazy<R?> = instanceForKClassOrNull(injector, tag)
