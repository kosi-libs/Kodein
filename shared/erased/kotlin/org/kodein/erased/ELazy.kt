package org.kodein.erased

import org.kodein.*

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
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factory(tag: Any? = null) = Factory<A, T>(erased(), erased(), tag)

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
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null) = FactoryOrNull<A, T>(erased(), erased(), tag)

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
inline fun <reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null) = Provider<T>(erased(), tag)

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null, arg: A) = Factory<A, T>(erased(), erased(), tag).toProvider { arg }

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null, crossinline fArg: () -> A) = Factory<A, T>(erased(), erased(), tag).toProvider(fArg)

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
inline fun <reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null) = ProviderOrNull<T>(erased(), tag)

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null, arg: A) = FactoryOrNull<A, T>(erased(), erased(), tag).toProviderOrNull { arg }

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null, crossinline fArg: () -> A) = FactoryOrNull<A, T>(erased(), erased(), tag).toProviderOrNull(fArg)

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
inline fun <reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null) = Instance<T>(erased(), tag)

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null, arg: A) = Factory<A, T>(erased(), erased(), tag).toInstance { arg }

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null, crossinline fArg: () -> A) = Factory<A, T>(erased(), erased(), tag).toInstance(fArg)

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
inline fun <reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null) = InstanceOrNull<T>(erased(), tag)

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null, arg: A) = FactoryOrNull<A, T>(erased(), erased(), tag).toInstanceOrNull { arg }

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null, crossinline fArg: () -> A) = FactoryOrNull<A, T>(erased(), erased(), tag).toInstanceOrNull(fArg)
