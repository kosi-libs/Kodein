package org.kodein.di.generic

import org.kodein.di.*

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDI.factory(tag: Any? = null) = Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.factoryOrNull(tag: Any? = null) = directDI.FactoryOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

/**
 * Gets all factories that can return a `T` for the given argument type, return type and tag.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching factories of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDI.allFactories(tag: Any? = null) = AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.provider(tag: Any? = null) = directDI.Provider<T>(org.kodein.type.generic(), tag)

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: A) = directDI.Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).toProvider { arg }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: Typed<A>) = directDI.Factory<A, T>(arg.type, org.kodein.type.generic(), tag).toProvider { arg.value }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, noinline fArg: () -> A) = directDI.Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).toProvider(fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null) = directDI.ProviderOrNull<T>(org.kodein.type.generic(), tag)

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: A) = directDI.FactoryOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)?.toProvider { arg }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = directDI.FactoryOrNull<A, T>(arg.type, org.kodein.type.generic(), tag)?.toProvider { arg.value }

/**
 * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of `T`, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = directDI.FactoryOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)?.toProvider(fArg)

/**
 * Gets all providers that can return a `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.allProviders(tag: Any? = null) = directDI.AllProviders<T>(org.kodein.type.generic(), tag)

/**
 * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, arg: A) = directDI.AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).map { it.toProvider { arg } }

/**
 * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, arg: Typed<A>) = directDI.AllFactories<A, T>(arg.type, org.kodein.type.generic(), tag).map { it.toProvider { arg.value } }

/**
 * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = directDI.AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).map { it.toProvider(fArg) }

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.instance(tag: Any? = null) = directDI.Instance<T>(org.kodein.type.generic(), tag)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: A) = directDI.Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).invoke(arg)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of `T`.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: Typed<A>) = directDI.Factory<A, T>(arg.type, org.kodein.type.generic(), tag).invoke(arg.value)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null) = directDI.InstanceOrNull<T>(org.kodein.type.generic(), tag)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A instance of `T`, or null if no provider was found.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: A) = directDI.FactoryOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)?.invoke(arg)

/**
 * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument, or null if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A instance of `T`, or null if no provider was found.
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = directDI.FactoryOrNull<A, T>(arg.type, org.kodein.type.generic(), tag)?.invoke(arg.value)

/**
 * Gets all instances that can return a `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DirectDIAware.allInstances(tag: Any? = null) = directDI.AllInstances<T>(org.kodein.type.generic(), tag)

/**
 * Gets all instances that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DirectDIAware.allInstances(tag: Any? = null, arg: A) = directDI.AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).map { it.invoke(arg) }

/**
 * Gets all instances that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DirectDIAware.allInstances(tag: Any? = null, arg: Typed<A>) = directDI.AllFactories<A, T>(arg.type, org.kodein.type.generic(), tag).map { it.invoke(arg.value) }

/**
 * Returns a `DirectDI` with its context and/or receiver changed.
 *
 * @param context The new context for the new DirectDI.
 * @param receiver The new receiver for the new DirectDI.
 */
inline fun <reified C : Any> DirectDIAware.on(context: C) = directDI.On(diContext(context))
