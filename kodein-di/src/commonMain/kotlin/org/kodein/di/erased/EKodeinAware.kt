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
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.factory(tag: Any? = null) = Factory<A, T>(erased(), erased(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.factoryOrNull(tag: Any? = null) = FactoryOrNull<A, T>(erased(), erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DIAware.provider(tag: Any? = null) = Provider<T>(erased(), tag)

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, arg: A) = Provider<A, T>(erased(), erased(), tag) { arg }

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DIAware.provider(tag: Any? = null, arg: Typed<A>) = Provider<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, noinline fArg: () -> A) = Provider<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DIAware.providerOrNull(tag: Any? = null) = ProviderOrNull<T>(erased(), tag)

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
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: A) = ProviderOrNull<A, T>(erased(), erased(), tag, { arg })

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
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = ProviderOrNull<A, T>(arg.type, erased(), tag, { arg.value })

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
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = ProviderOrNull<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DIAware.instance(tag: Any? = null) = Instance<T>(erased(), tag)

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, arg: A) = Instance<A, T>(erased(), erased(), tag) { arg }

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DIAware.instance(tag: Any? = null, arg: Typed<A>) = Instance<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, noinline fArg: () -> A) = Instance<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DIAware.instanceOrNull(tag: Any? = null) = InstanceOrNull<T>(erased(), tag)

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
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: A) = InstanceOrNull<A, T>(erased(), erased(), tag) { arg }

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
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = InstanceOrNull<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, noinline fArg: () -> A) = InstanceOrNull<A, T>(erased(), erased(), tag, fArg)

/**
 * Defines a context and its type to be used by DI.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diContext(context)"), DeprecationLevel.ERROR)
inline fun <reified C : Any> kcontext(context: C) = DIContext(erased(), context)
inline fun <reified C : Any> diContext(context: C) = DIContext(erased(), context)

/**
 * Defines a context and its type to be used by DI.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diContext(getContext)"), DeprecationLevel.ERROR)
inline fun <reified C : Any> kcontext(crossinline getContext: () -> C) = DIContext<C>(erased()) { getContext() }
inline fun <reified C : Any> diContext(crossinline getContext: () -> C) = DIContext<C>(erased()) { getContext() }

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param context The new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
inline fun <reified C : Any> DIAware.on(context: C, trigger: DITrigger? = this.diTrigger) = On(diContext(context), trigger)

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param getContext A function that gets the new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
inline fun <reified C : Any> DIAware.on(trigger: DITrigger? = this.diTrigger, crossinline getContext: () -> C) = On(diContext(getContext), trigger)

/**
 * Allows to create a new DI object with a trigger set.
 *
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
fun DIAware.on(trigger: DITrigger?) = On(diContext, trigger)
