package org.kodein.di.erased

import org.kodein.di.*

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.factory(tag: Any? = null) = Factory<A, T>(erased(), erased(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.factoryOrNull(tag: Any? = null) = FactoryOrNull<A, T>(erased(), erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.provider(tag: Any? = null) = Provider<T>(erased(), tag)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.provider(tag: Any? = null, arg: A) = Provider<A, T>(erased(), erased(), tag) { arg }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.provider(tag: Any? = null, arg: Typed<A>) = Provider<A, T>(arg.type, erased(), tag) { arg.value }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.provider(tag: Any? = null, noinline fArg: () -> A) = Provider<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.providerOrNull(tag: Any? = null) = ProviderOrNull<T>(erased(), tag)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.providerOrNull(tag: Any? = null, arg: A) = ProviderOrNull<A, T>(erased(), erased(), tag, { arg })

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = ProviderOrNull<A, T>(arg.type, erased(), tag, { arg.value })

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = ProviderOrNull<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.instance(tag: Any? = null) = Instance<T>(erased(), tag)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.instance(tag: Any? = null, arg: A) = Instance<A, T>(erased(), erased(), tag) { arg }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.instance(tag: Any? = null, arg: Typed<A>) = Instance<A, T>(arg.type, erased(), tag) { arg.value }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.instance(tag: Any? = null, noinline fArg: () -> A) = Instance<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.instanceOrNull(tag: Any? = null) = InstanceOrNull<T>(erased(), tag)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.instanceOrNull(tag: Any? = null, arg: A) = InstanceOrNull<A, T>(erased(), erased(), tag) { arg }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = InstanceOrNull<A, T>(arg.type, erased(), tag) { arg.value }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.instanceOrNull(tag: Any? = null, noinline fArg: () -> A) = InstanceOrNull<A, T>(erased(), erased(), tag, fArg)

/**
 * Defines a context and its type to be used by Kodein.
 */
inline fun <reified C> kcontext(context: C) = KodeinContext(erased(), context)

/**
 * Allows to create a new Kodein object with a context and/or a trigger set.
 *
 * @param context The new context of the new Kodein.
 * @param trigger The new trigger of the new Kodein.
 * @return A Kodein object that uses the same container as this one, but with its context and/or trigger changed.
 */
inline fun <reified C> KodeinAware.on(context: C, trigger: KodeinTrigger? = this.kodeinTrigger) = On(kcontext(context), trigger)

/**
 * Allows to create a new Kodein object with a trigger set.
 *
 * @param trigger The new trigger of the new Kodein.
 * @return A Kodein object that uses the same container as this one, but with its context and/or trigger changed.
 */
fun KodeinAware.on(trigger: KodeinTrigger?) = On(kodeinContext, trigger)
