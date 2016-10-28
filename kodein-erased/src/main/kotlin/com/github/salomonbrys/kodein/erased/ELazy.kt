package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.*

/**
 * Gets a lazy factory for the given type, tag and argument type.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of `T`.
 * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factory(tag: Any? = null) = erasedFactory<A, T>(tag)

/**
 * Gets a lazy factory for the given type, tag and argument type, or null if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of `T`, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null) = erasedFactoryOrNull<A, T>(tag)

/**
 * Gets a lazy provider for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws Kodein.NotFoundException When accessing the property, if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null) = erasedProvider<T>(tag)

/**
 * Gets a lazy provider for the given type and tag, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null) = erasedProviderOrNull<T>(tag)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of `T`.
 * @throws Kodein.NotFoundException When accessing the property, if no provider was found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null) = erasedInstance<T>(tag)

/**
 * Gets a lazy instance for the given type and tag, or null is none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of `T`, or null if no provider is found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null) = erasedInstanceOrNull<T>(tag)

/**
 * Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedLazyKodeinFactory<A>.provider(tag: Any? = null) = erasedProvider<A, T>(tag)

/**
 * Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`, or null if no factory is found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedLazyKodeinFactory<A>.providerOrNull(tag: Any? = null) = erasedProviderOrNull<A, T>(tag)

/**
 * Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy instance of `T`.
 * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedLazyKodeinFactory<A>.instance(tag: Any? = null) = erasedInstance<A, T>(tag)

/**
 * Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy instance of `T`, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedLazyKodeinFactory<A>.instanceOrNull(tag: Any? = null) = erasedInstanceOrNull<A, T>(tag)


/**
 * Allows to get a lazy provider or instance from a curried factory with an `A` argument.
 *
 * A generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> LazyKodeinAwareBase.with(noinline arg: () -> A) = withErased(arg)

/**
 * Allows to get a lazy provider or instance from a curried factory with an `A` argument.
 *
 * A generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> LazyKodeinAwareBase.with(arg: A) = withErased(arg)
