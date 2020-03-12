package org.kodein.di.erased

import org.kodein.di.*

/**
 * Gets a factory of `T` for the given argument type and return type.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @return A factory.
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.factory() = Factory<A, T>(erased(), erased())

/**
 * Gets a factory of `T` for the given argument type and return type, or nul if none is found.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @return A factory, or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.factoryOrNull() = FactoryOrNull<A, T>(erased(), erased())

/**
 * Gets a provider of `T` for the given type.
 * The name of the receiving property is used as tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> Named.provider() = Provider<T>(erased())

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.provider(arg: A) = Provider<A, T>(erased(), erased()) { arg }

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A : Any, reified T : Any> Named.provider(arg: Typed<A>) = Provider<A, T>(arg.type, erased()) { arg.value }

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.provider(noinline fArg: () -> A) = Provider<A, T>(erased(), erased(), fArg)

/**
 * Gets a provider of `T` for the given type, or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @return A provider, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> Named.providerOrNull() = ProviderOrNull<T>(erased())

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.providerOrNull(arg: A) = ProviderOrNull<A, T>(erased(), erased(), { arg })

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> Named.providerOrNull(arg: Typed<A>) = ProviderOrNull<A, T>(arg.type, erased(), { arg.value })

/**
 * Gets a provider of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.providerOrNull(noinline fArg: () -> A) = ProviderOrNull<A, T>(erased(), erased(), fArg)

/**
 * Gets an instance of `T` for the given type.
 * The name of the receiving property is used as tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> Named.instance() = Instance<T>(erased())

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.instance(arg: A) = Instance<A, T>(erased(), erased()) { arg }

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> Named.instance(arg: Typed<A>) = Instance<A, T>(arg.type, erased()) { arg.value }

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A].
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.instance(noinline fArg: () -> A) = Instance<A, T>(erased(), erased(), fArg)

/**
 * Gets an instance of `T` for the given type, or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @return An instance, or null if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> Named.instanceOrNull() = InstanceOrNull<T>(erased())

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(arg: A) = InstanceOrNull<A, T>(erased(), erased()) { arg }

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> Named.instanceOrNull(arg: Typed<A>) = InstanceOrNull<A, T>(arg.type, erased()) { arg.value }

/**
 * Gets an instance of [T] for the given type, curried from a factory that takes an argument [A], or null if none is found.
 * The name of the receiving property is used as tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(noinline fArg: () -> A) = InstanceOrNull<A, T>(erased(), erased(), fArg)

/**
 * Gets a constant of type [T] and tag whose tag is the name of the receiving property.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DIAware.constant() = Constant<T>(erased())
