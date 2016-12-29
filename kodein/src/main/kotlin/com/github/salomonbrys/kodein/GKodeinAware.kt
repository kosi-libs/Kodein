package com.github.salomonbrys.kodein

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factory(tag: Any? = null) = genericFactory<A, T>(tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factoryOrNull(tag: Any? = null) = genericFactoryOrNull<A, T>(tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.provider(tag: Any? = null) = genericProvider<T>(tag)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null) = genericProviderOrNull<T>(tag)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.instance(tag: Any? = null) = genericInstance<T>(tag)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null) = genericInstanceOrNull<T>(tag)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedKodeinFactory<*>.provider(tag: Any? = null) = genericProvider<T>(tag)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedKodeinFactory<*>.providerOrNull(tag: Any? = null) = genericProviderOrNull<T>(tag)

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedKodeinFactory<*>.instance(tag: Any? = null) = genericInstance<T>(tag)

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedKodeinFactory<*>.instanceOrNull(tag: Any? = null) = genericInstanceOrNull<T>(tag)

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.with(noinline arg: () -> A) = withGeneric(arg)

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.with(arg: A) = withGeneric(arg)
