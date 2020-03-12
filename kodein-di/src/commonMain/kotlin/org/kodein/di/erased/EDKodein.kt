package org.kodein.di.erased

import org.kodein.di.DirectDIAware
import org.kodein.di.Typed
import org.kodein.di.erased

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.factory(tag: Any? = null) = directDI.Factory<A, T>(erased(), erased(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.factoryOrNull(tag: Any? = null) = directDI.FactoryOrNull<A, T>(erased(), erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be erased.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.provider(tag: Any? = null) = directDI.Provider<T>(erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: A) = directDI.Provider<A, T>(erased(), erased(), tag) { arg }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: Typed<A>) = directDI.Provider<A, T>(arg.type, erased(), tag) { arg.value }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, noinline fArg: () -> A) = directDI.Provider<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null) = directDI.ProviderOrNull<T>(erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: A) = directDI.ProviderOrNull<A, T>(erased(), erased(), tag) { arg }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = directDI.ProviderOrNull<A, T>(arg.type, erased(), tag) { arg.value }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = directDI.ProviderOrNull<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.instance(tag: Any? = null) = directDI.Instance<T>(erased(), tag)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: A) = directDI.Instance<A, T>(erased(), erased(), tag, arg)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: Typed<A>) = directDI.Instance<A, T>(arg.type, erased(), tag, arg.value)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null) = directDI.InstanceOrNull<T>(erased(), tag)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A instance of `T`, or null if no provider was found.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: A) = directDI.InstanceOrNull<A, T>(erased(), erased(), tag, arg)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A instance of `T`, or null if no provider was found.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = directDI.InstanceOrNull<A, T>(arg.type, erased(), tag, arg.value)

/**
 * Returns a `DirectDI` with its context and/or receiver changed.
 *
 * @param context The new context for the new DirectDI.
 * @param receiver The new receiver for the new DirectDI.
 */
inline fun <reified C : Any> DirectDIAware.on(context: C) = directDI.On(diContext(context))
