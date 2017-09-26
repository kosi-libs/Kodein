package org.kodein

/**
 * Gets a lazy factory for the given type, tag and argument type.
 *
 * A & T generics will be kept.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.factory(tag: Any? = null) = injector.Factory<A, T>(generic(), generic(), tag)

/**
 * Gets a lazy factory for the given type, tag and argument type, or null if none is found
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a factory of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.factoryOrNull(tag: Any? = null) = injector.FactoryOrNull<A, T>(generic(), generic(), tag)

/**
 * Gets a lazy provider for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.provider(tag: Any? = null) = injector.Provider<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinInjectedBase.provider(tag: Any? = null, arg: A) = injector.Factory<A, T>(generic(), generic(), tag).toProvider { arg }

inline fun <reified A, reified T : Any> KodeinInjectedBase.provider(tag: Any? = null, crossinline fArg: () -> A) = injector.Factory<A, T>(generic(), generic(), tag).toProvider(fArg)

/**
 * Gets a lazy provider for the given type and tag, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no provider was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null) = injector.ProviderOrNull<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null, arg: A) = injector.FactoryOrNull<A, T>(generic(), generic(), tag).toProviderOrNull { arg }

inline fun <reified A, reified T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null, crossinline fArg: () -> A) = injector.FactoryOrNull<A, T>(generic(), generic(), tag).toProviderOrNull(fArg)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.instance(tag: Any? = null) = injector.Instance<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinInjectedBase.instance(tag: Any? = null, arg: A) = injector.Factory<A, T>(generic(), generic(), tag).toInstance { arg }

inline fun <reified A, reified T : Any> KodeinInjectedBase.instance(tag: Any? = null, crossinline fArg: () -> A) = injector.Factory<A, T>(generic(), generic(), tag).toInstance(fArg)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null) = injector.InstanceOrNull<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null, arg: A) = injector.FactoryOrNull<A, T>(generic(), generic(), tag).toInstanceOrNull { arg }

inline fun <reified A, reified T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null, crossinline fArg: () -> A) = injector.FactoryOrNull<A, T>(generic(), generic(), tag).toInstanceOrNull(fArg)
