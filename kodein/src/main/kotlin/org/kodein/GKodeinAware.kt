package org.kodein

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
inline fun <reified A, reified T : Any> KodeinAwareBase.factory(tag: Any? = null) = kodein.Factory<A, T>(generic(), generic(), tag)

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
inline fun <reified A, reified T : Any> KodeinAwareBase.factoryOrNull(tag: Any? = null) = kodein.FactoryOrNull<A, T>(generic(), generic(), tag)

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
inline fun <reified T : Any> KodeinAwareBase.provider(tag: Any? = null) = kodein.Provider<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinAwareBase.provider(tag: Any? = null, arg: A) = Factory<A, T>(generic(), generic(), tag).toProvider { arg }

inline fun <reified A, reified T : Any> KodeinAwareBase.provider(tag: Any? = null, crossinline fArg: () -> A) = Factory<A, T>(generic(), generic(), tag).toProvider(fArg)


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
inline fun <reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null) = kodein.ProviderOrNull<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null, arg: A) = FactoryOrNull<A, T>(generic(), generic(), tag)?.toProvider { arg }

inline fun <reified A, reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null, crossinline fArg: () -> A) = FactoryOrNull<A, T>(generic(), generic(), tag)?.toProvider(fArg)


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
inline fun <reified T : Any> KodeinAwareBase.instance(tag: Any? = null) = kodein.Instance<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinAwareBase.instance(tag: Any? = null, arg: A) = Factory<A, T>(generic(), generic(), tag).invoke(arg)

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
inline fun <reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null) = kodein.InstanceOrNull<T>(generic(), tag)

inline fun <reified A, reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null, arg: A) = FactoryOrNull<A, T>(generic(), generic(), tag)?.invoke(arg)
