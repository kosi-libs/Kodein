package org.kodein.di

import org.kodein.type.generic

//region Standard retrieving
/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.factory(tag: Any? = null): LazyDelegate<(A) -> T> =
    Factory<A, T>(generic(), generic(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.factoryOrNull(tag: Any? = null): LazyDelegate<((A) -> T)?> =
    FactoryOrNull<A, T>(generic(), generic(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
public inline fun <reified T : Any> DIAware.provider(tag: Any? = null): LazyDelegate<() -> T> =
    Provider<T>(generic(), tag)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.provider(
    tag: Any? = null,
    arg: A,
): LazyDelegate<() -> T> = Provider<A, T>(generic(), generic(), tag) { arg }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <A, reified T : Any> DIAware.provider(
    tag: Any? = null,
    arg: Typed<A>,
): LazyDelegate<() -> T> = Provider<A, T>(arg.type, generic(), tag) { arg.value }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.provider(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<() -> T> = Provider<A, T>(generic(), generic(), tag, fArg)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
public inline fun <reified T : Any> DIAware.providerOrNull(tag: Any? = null): LazyDelegate<(() -> T)?> =
    ProviderOrNull<T>(generic(), tag)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(
    tag: Any? = null,
    arg: A,
): LazyDelegate<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), tag, { arg })

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <A, reified T : Any> DIAware.providerOrNull(
    tag: Any? = null,
    arg: Typed<A>,
): LazyDelegate<(() -> T)?> = ProviderOrNull<A, T>(arg.type, generic(), tag, { arg.value })

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
public inline fun <reified T : Any> DIAware.instance(tag: Any? = null): LazyDelegate<T> = Instance<T>(generic(), tag)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
public inline fun <reified A : Any, reified T : Any> DIAware.instance(
    tag: Any? = null,
    arg: A,
): LazyDelegate<T> = Instance<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DIAware.instance(
    tag: Any? = null,
    arg: Typed<A>,
): LazyDelegate<T> = Instance<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DIAware.instance(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<T> = Instance<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DIAware.instanceOrNull(tag: Any? = null): LazyDelegate<T?> =
    InstanceOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(
    tag: Any? = null,
    arg: A,
): LazyDelegate<T?> = InstanceOrNull<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DIAware.instanceOrNull(
    tag: Any? = null,
    arg: Typed<A>,
): LazyDelegate<T?> = InstanceOrNull<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<T?> = InstanceOrNull<A, T>(generic(), generic(), tag, fArg)

/**
 * Defines a context and its type to be used by DI.
 */
public inline fun <reified C : Any> diContext(context: C): DIContext<C> = DIContext(generic(), context)

/**
 * Defines a context and its type to be used by DI.
 */
public inline fun <reified C : Any> diContext(crossinline getContext: () -> C) : DIContext<C> =
    DIContext(generic<C>(), getValue = { getContext() })

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param context The new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
public inline fun <reified C : Any> DIAware.on(
    context: C,
    trigger: DITrigger? = this.diTrigger,
): DI = On(diContext(context), trigger)

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param getContext A function that gets the new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
public inline fun <reified C : Any> DIAware.on(
    trigger: DITrigger? = this.diTrigger,
    crossinline getContext: () -> C,
): DI = On(diContext(getContext), trigger)

/**
 * Allows to create a new DI object with a trigger set.
 *
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
public fun DIAware.on(trigger: DITrigger?): DI = On(diContext, trigger)


//endregion

//region Direct retrieving
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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.factory(tag: Any? = null): (A) -> T =
    directDI.Factory<A, T>(generic(), generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.factoryOrNull(tag: Any? = null): ((A) -> T)? =
    directDI.FactoryOrNull<A, T>(generic(), generic(), tag)

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
public inline fun <reified T : Any> DirectDIAware.provider(tag: Any? = null): () -> T =
    directDI.Provider<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(
    tag: Any? = null,
    arg: A,
): () -> T = directDI.Provider<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DirectDIAware.provider(
    tag: Any? = null,
    arg: Typed<A>,
): () -> T = directDI.Provider<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(
    tag: Any? = null,
    noinline fArg: () -> A,
): () -> T = directDI.Provider<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null): (() -> T)? =
    directDI.ProviderOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(
    tag: Any? = null,
    arg: A,
): (() -> T)? = directDI.ProviderOrNull<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DirectDIAware.providerOrNull(
    tag: Any? = null,
    arg: Typed<A>,
): (() -> T)? = directDI.ProviderOrNull<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(
    tag: Any? = null,
    noinline fArg: () -> A,
): (() -> T)? = directDI.ProviderOrNull<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DirectDIAware.instance(tag: Any? = null): T = directDI.Instance<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: A): T =
    directDI.Instance<A, T>(generic(), generic(), tag, arg)

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
public inline fun <A, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: Typed<A>): T =
    directDI.Instance<A, T>(arg.type, generic(), tag, arg.value)

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
public inline fun <reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null): T? =
    directDI.InstanceOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: A): T? =
    directDI.InstanceOrNull<A, T>(generic(), generic(), tag, arg)

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
public inline fun <A, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>): T? =
    directDI.InstanceOrNull<A, T>(arg.type, generic(), tag, arg.value)

/**
 * Returns a `DirectDI` with its context and/or receiver changed.
 *
 * @param context The new context for the new DirectDI.
 * @param receiver The new receiver for the new DirectDI.
 */
public inline fun <reified C : Any> DirectDIAware.on(context: C): DirectDI = directDI.On(diContext(context))
//endregion

//region Named retrieving
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
public inline fun <reified A : Any, reified T : Any> Named.factory(): LazyDelegate<(A) -> T> =
    Factory<A, T>(generic(), generic())

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
public inline fun <reified A : Any, reified T : Any> Named.factoryOrNull(): LazyDelegate<((A) -> T)?> =
    FactoryOrNull<A, T>(generic(), generic())

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
public inline fun <reified T : Any> Named.provider(): LazyDelegate<() -> T> = Provider<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.provider(arg: A): LazyDelegate<() -> T> =
    Provider<A, T>(generic(), generic()) { arg }

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
public inline fun <A : Any, reified T : Any> Named.provider(arg: Typed<A>): LazyDelegate<() -> T> =
    Provider<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.provider(noinline fArg: () -> A): LazyDelegate<() -> T> =
    Provider<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.providerOrNull(): LazyDelegate<(() -> T)?> = ProviderOrNull<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.providerOrNull(arg: A): LazyDelegate<(() -> T)?> =
    ProviderOrNull<A, T>(generic(), generic(), { arg })

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
public inline fun <A, reified T : Any> Named.providerOrNull(arg: Typed<A>): LazyDelegate<(() -> T)?> =
    ProviderOrNull<A, T>(arg.type, generic(), { arg.value })

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
public inline fun <reified A : Any, reified T : Any> Named.providerOrNull(noinline fArg: () -> A): LazyDelegate<(() -> T)?> =
    ProviderOrNull<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.instance(): LazyDelegate<T> = Instance<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.instance(arg: A): LazyDelegate<T> =
    Instance<A, T>(generic(), generic()) { arg }

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
public inline fun <A, reified T : Any> Named.instance(arg: Typed<A>): LazyDelegate<T> =
    Instance<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.instance(noinline fArg: () -> A): LazyDelegate<T> =
    Instance<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.instanceOrNull(): LazyDelegate<T?> = InstanceOrNull<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(arg: A): LazyDelegate<T?> =
    InstanceOrNull<A, T>(generic(), generic()) { arg }

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
public inline fun <A, reified T : Any> Named.instanceOrNull(arg: Typed<A>): LazyDelegate<T?> =
    InstanceOrNull<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(noinline fArg: () -> A): LazyDelegate<T?> =
    InstanceOrNull<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> DIAware.constant(): LazyDelegate<T> = Constant<T>(generic())
//endregion
