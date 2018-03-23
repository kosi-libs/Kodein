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
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodein.factory(tag: Any? = null) = Factory<A, T>(generic(), generic(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.factoryOrNull(tag: Any? = null) = dkodein.FactoryOrNull<A, T>(generic(), generic(), tag)

/**
 * Gets all factories that can return a `T` for the given argument type, return type and tag.
 *
 * A & T generics will be preserved.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching factories of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodein.allFactories(tag: Any? = null) = AllFactories<A, T>(generic(), generic(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.provider(tag: Any? = null) = dkodein.Provider<T>(generic(), tag)

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.provider(tag: Any? = null, arg: A) = dkodein.Factory<A, T>(generic(), generic(), tag).toProvider { arg }

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.provider(tag: Any? = null, arg: Typed<A>) = dkodein.Factory<A, T>(arg.type, generic(), tag).toProvider { arg.value }

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.provider(tag: Any? = null, noinline fArg: () -> A) = dkodein.Factory<A, T>(generic(), generic(), tag).toProvider(fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved.
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null) = dkodein.ProviderOrNull<T>(generic(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null, arg: A) = dkodein.FactoryOrNull<A, T>(generic(), generic(), tag)?.toProvider { arg }

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
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = dkodein.FactoryOrNull<A, T>(arg.type, generic(), tag)?.toProvider { arg.value }

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
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = dkodein.FactoryOrNull<A, T>(generic(), generic(), tag)?.toProvider(fArg)

/**
 * Gets all providers that can return a `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching providers of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.allProviders(tag: Any? = null) = dkodein.AllProviders<T>(generic(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, arg: A) = dkodein.AllFactories<A, T>(generic(), generic(), tag).map { it.toProvider { arg } }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, arg: Typed<A>) = dkodein.AllFactories<A, T>(arg.type, generic(), tag).map { it.toProvider { arg.value } }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = dkodein.AllFactories<A, T>(generic(), generic(), tag).map { it.toProvider(fArg) }

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.instance(tag: Any? = null) = dkodein.Instance<T>(generic(), tag)

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.instance(tag: Any? = null, arg: A) = dkodein.Factory<A, T>(generic(), generic(), tag).invoke(arg)

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.instance(tag: Any? = null, arg: Typed<A>) = dkodein.Factory<A, T>(arg.type, generic(), tag).invoke(arg.value)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.instanceOrNull(tag: Any? = null) = dkodein.InstanceOrNull<T>(generic(), tag)

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.instanceOrNull(tag: Any? = null, arg: A) = dkodein.FactoryOrNull<A, T>(generic(), generic(), tag)?.invoke(arg)

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = dkodein.FactoryOrNull<A, T>(arg.type, generic(), tag)?.invoke(arg.value)

/**
 * Gets all instances that can return a `T` for the given type and tag.
 *
 * T generics will be preserved.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching instances of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.allInstances(tag: Any? = null) = dkodein.AllInstances<T>(generic(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allInstances(tag: Any? = null, arg: A) = dkodein.AllFactories<A, T>(generic(), generic(), tag).map { it.invoke(arg) }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.allInstances(tag: Any? = null, arg: Typed<A>) = dkodein.AllFactories<A, T>(arg.type, generic(), tag).map { it.invoke(arg.value) }

/**
 * Returns a `DKodein` with its context and/or receiver changed.
 *
 * @param context The new context for the new DKodein.
 * @param receiver The new receiver for the new DKodein.
 */
inline fun <reified C> DKodeinAware.on(context: C, receiver: Any? = DKodeinBase.SAME_RECEIVER) = dkodein.On(kcontext(context), receiver)

/**
 * Returns a `DKodein` with its receiver changed.
 *
 * Can only be used with a named parameter: `dkodein.on(receiver = this)`
 *
 * @param receiver The new receiver for the new DKodein.
 */
fun DKodeinAware.on(@Suppress("UNUSED_PARAMETER") _0: Nothing? = null, receiver: Any?) = dkodein.On(DKodeinBase.SAME_CONTEXT, receiver)
