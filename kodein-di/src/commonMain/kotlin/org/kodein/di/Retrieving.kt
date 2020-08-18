package org.kodein.di

import org.kodein.di.bindings.Reference
import org.kodein.di.bindings.Strong
import org.kodein.di.bindings.Weak
import org.kodein.type.generic
import kotlin.js.JsName
import kotlin.jvm.JvmName

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
public inline fun <reified A : Any, reified T : Any> DIAware.factory(tag: Any? = null): DIProperty<(A) -> T> = Factory<A, T>(generic(), generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.factoryOrNull(tag: Any? = null): DIProperty<((A) -> T)?> = FactoryOrNull<A, T>(generic(), generic(), tag)

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
public inline fun <reified T : Any> DIAware.provider(tag: Any? = null): DIProperty<() -> T> = Provider<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, arg: A): DIProperty<() -> T> = Provider<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DIAware.provider(tag: Any? = null, arg: Typed<A>): DIProperty<() -> T> = Provider<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, noinline fArg: () -> A): DIProperty<() -> T> = Provider<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DIAware.providerOrNull(tag: Any? = null): DIProperty<(() -> T)?> = ProviderOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: A): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), tag, { arg })

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
public inline fun <A, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: Typed<A>): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(arg.type, generic(), tag, { arg.value })

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
public inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DIAware.instance(tag: Any? = null): DIProperty<T> = Instance<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, arg: A): DIProperty<T> = Instance<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DIAware.instance(tag: Any? = null, arg: Typed<A>): DIProperty<T> = Instance<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, noinline fArg: () -> A): DIProperty<T> = Instance<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DIAware.instanceOrNull(tag: Any? = null): DIProperty<T?> = InstanceOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: A): DIProperty<T?> = InstanceOrNull<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>): DIProperty<T?> = InstanceOrNull<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, noinline fArg: () -> A): DIProperty<T?> = InstanceOrNull<A, T>(generic(), generic(), tag, fArg)

public inline fun <reified C : Any> diContextRef(context: Reference<C>): DIContext<C> = DIContext(generic(), context)

public inline fun <reified C : Any> diContextRef(noinline getContext: () -> Reference<C>): DIContext<C> = DIContext(generic(), getContext)

/**
 * Defines a context and its type to be used by DI.
 */
public inline fun <reified C : Any> diContext(context: C, ref: Reference.Maker): DIContext<C> = DIContext(generic(), context, ref)

/**
 * Defines a context and its type to be used by DI.
 */
public inline fun <reified C : Any> diContext(ref: Reference.Maker, noinline getContext: () -> C) : DIContext<C> = DIContext(generic<C>(), getContext, ref)

// Since 7.1
@Deprecated("Specify explicitely the context reference to avoid memory leaks", ReplaceWith("diContext(Strong, getContext)", "org.kodein.di.bindings.Strong"))
@JvmName("diStrongContext") @JsName("diStrongContext")
public inline fun <reified C : Any> diContext(noinline getContext: () -> C) : DIContext<C> = diContext(Strong, getContext)

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param context The new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
public inline fun <reified C : Any> DIAware.on(context: C, ref: Reference.Maker = Strong, trigger: DITrigger? = this.diTrigger): DI = On(diContext(context, ref), trigger)

public inline fun <reified C : Any> DIAware.onRef(context: Reference<C>, trigger: DITrigger? = this.diTrigger): DI = On(diContextRef(context), trigger)

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param getContext A function that gets the new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
public inline fun <reified C : Any> DIAware.on(trigger: DITrigger? = this.diTrigger, ref: Reference.Maker = Strong, noinline getContext: () -> C): DI = On(diContext(ref, getContext), trigger)

public inline fun <reified C : Any> DIAware.onRef(trigger: DITrigger? = this.diTrigger, noinline getContext: () -> Reference<C>): DI = On(diContextRef(getContext), trigger)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.factory(tag: Any? = null): (A) -> T = directDI.Factory<A, T>(generic(), generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.factoryOrNull(tag: Any? = null): ((A) -> T)? = directDI.FactoryOrNull<A, T>(generic(), generic(), tag)

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
public inline fun <reified T : Any> DirectDIAware.provider(tag: Any? = null): () -> T = directDI.Provider<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: A): () -> T = directDI.Provider<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DirectDIAware.provider(tag: Any? = null, arg: Typed<A>): () -> T = directDI.Provider<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.provider(tag: Any? = null, noinline fArg: () -> A): () -> T = directDI.Provider<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null): (() -> T)? = directDI.ProviderOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: A): (() -> T)? = directDI.ProviderOrNull<A, T>(generic(), generic(), tag) { arg }

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
public inline fun <A, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, arg: Typed<A>): (() -> T)? = directDI.ProviderOrNull<A, T>(arg.type, generic(), tag) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A): (() -> T)? = directDI.ProviderOrNull<A, T>(generic(), generic(), tag, fArg)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: A): T = directDI.Instance<A, T>(generic(), generic(), tag, arg)

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
public inline fun <A, reified T : Any> DirectDIAware.instance(tag: Any? = null, arg: Typed<A>): T = directDI.Instance<A, T>(arg.type, generic(), tag, arg.value)

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
public inline fun <reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null): T? = directDI.InstanceOrNull<T>(generic(), tag)

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
public inline fun <reified A : Any, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: A): T? = directDI.InstanceOrNull<A, T>(generic(), generic(), tag, arg)

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
public inline fun <A, reified T : Any> DirectDIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>): T? = directDI.InstanceOrNull<A, T>(arg.type, generic(), tag, arg.value)

/**
 * Returns a `DirectDI` with its context and/or receiver changed.
 *
 * @param context The new context for the new DirectDI.
 * @param receiver The new receiver for the new DirectDI.
 */
public inline fun <reified C : Any> DirectDIAware.on(context: C, ref: Reference.Maker = Strong): DirectDI = directDI.On(diContext(context, ref))
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
public inline fun <reified A : Any, reified T : Any> Named.factory(): DIProperty<(A) -> T> = Factory<A, T>(generic(), generic())

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
public inline fun <reified A : Any, reified T : Any> Named.factoryOrNull(): DIProperty<((A) -> T)?> = FactoryOrNull<A, T>(generic(), generic())

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
public inline fun <reified T : Any> Named.provider(): DIProperty<() -> T> = Provider<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.provider(arg: A): DIProperty<() -> T> = Provider<A, T>(generic(), generic()) { arg }

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
public inline fun <A : Any, reified T : Any> Named.provider(arg: Typed<A>): DIProperty<() -> T> = Provider<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.provider(noinline fArg: () -> A): DIProperty<() -> T> = Provider<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.providerOrNull(): DIProperty<(() -> T)?> = ProviderOrNull<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.providerOrNull(arg: A): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), { arg })

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
public inline fun <A, reified T : Any> Named.providerOrNull(arg: Typed<A>): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(arg.type, generic(), { arg.value })

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
public inline fun <reified A : Any, reified T : Any> Named.providerOrNull(noinline fArg: () -> A): DIProperty<(() -> T)?> = ProviderOrNull<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.instance(): DIProperty<T> = Instance<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.instance(arg: A): DIProperty<T> = Instance<A, T>(generic(), generic()) { arg }

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
public inline fun <A, reified T : Any> Named.instance(arg: Typed<A>): DIProperty<T> = Instance<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.instance(noinline fArg: () -> A): DIProperty<T> = Instance<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> Named.instanceOrNull(): DIProperty<T?> = InstanceOrNull<T>(generic())

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
public inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(arg: A): DIProperty<T?> = InstanceOrNull<A, T>(generic(), generic()) { arg }

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
public inline fun <A, reified T : Any> Named.instanceOrNull(arg: Typed<A>): DIProperty<T?> = InstanceOrNull<A, T>(arg.type, generic()) { arg.value }

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
public inline fun <reified A : Any, reified T : Any> Named.instanceOrNull(noinline fArg: () -> A): DIProperty<T?> = InstanceOrNull<A, T>(generic(), generic(), fArg)

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
public inline fun <reified T : Any> DIAware.constant(): DIProperty<T> = Constant<T>(generic())
//endregion
