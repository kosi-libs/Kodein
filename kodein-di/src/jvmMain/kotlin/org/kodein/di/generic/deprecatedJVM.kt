package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.TypeBinderSubTypes
import org.kodein.type.TypeToken

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("factory<A, T>(tag)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.factory(tag: Any? = null) = Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("factoryOrNull<A, T>(tag)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.factoryOrNull(tag: Any? = null) = FactoryOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

/**
 * Gets all factories that match the the given argument type, return type and tag.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the factories take.
 * @param T The type of object to retrieve with the factories.
 * @param tag The bound tag, if any.
 * @return A list of factories of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allFactories<A, T>(tag)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allFactories(tag: Any? = null) = AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("provider<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.provider(tag: Any? = null) = Provider<T>(org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("provider<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, arg: A) = Provider<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("provider<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.provider(tag: Any? = null, arg: Typed<A>) = Provider<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("provider<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.provider(tag: Any? = null, noinline fArg: () -> A) = Provider<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("providerOrNull<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.providerOrNull(tag: Any? = null) = ProviderOrNull<T>(org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("providerOrNull<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: A) = ProviderOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("providerOrNull<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.providerOrNull(tag: Any? = null, arg: Typed<A>) = ProviderOrNull<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("providerOrNull<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = ProviderOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

/**
 * Gets all providers that match the the given return type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.allProviders(tag: Any? = null) = AllProviders<T>(org.kodein.type.generic(), tag)

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allProviders(tag: Any? = null, arg: A) = AllProviders<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.allProviders(tag: Any? = null, arg: Typed<A>) = AllProviders<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = AllProviders<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instance<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.instance(tag: Any? = null) = Instance<T>(org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instance<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, arg: A) = Instance<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instance<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A : Any, reified T : Any> DIAware.instance(tag: Any? = null, arg: Typed<A>) = Instance<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instance<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.instance(tag: Any? = null, noinline fArg: () -> A) = Instance<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instanceOrNull<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.instanceOrNull(tag: Any? = null) = InstanceOrNull<T>(org.kodein.type.generic(), tag)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instanceOrNull<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: A) = InstanceOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instanceOrNull<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, arg: Typed<A>) = InstanceOrNull<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("instanceOrNull<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.instanceOrNull(tag: Any? = null, noinline fArg: () -> A) = InstanceOrNull<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

/**
 * Gets all instances from providers that match the the given return type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DIAware.allInstances(tag: Any? = null) = AllInstances<T>(org.kodein.type.generic(), tag)

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allInstances(tag: Any? = null, arg: A) = AllInstances<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, { arg })

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.allInstances(tag: Any? = null, arg: Typed<A>) = AllInstances<A, T>(arg.type, org.kodein.type.generic(), tag, { arg.value })

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allInstances(tag: Any? = null, noinline fArg: () -> A) = AllInstances<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

/**
 * Defines a context and its type to be used by DI.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diContext<C>(context)", "org.kodein.di"), DeprecationLevel.ERROR)
inline fun <reified C : Any> kcontext(context: C) = DIContext(org.kodein.type.generic(), context)

/**
 * Defines a context and its type to be used by DI.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diContext<C>(getContext)", "org.kodein.di"), DeprecationLevel.ERROR)
inline fun <reified C : Any> kcontext(crossinline getContext: () -> C) = DIContext<C>(org.kodein.type.generic()) { getContext() }

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param context The new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("on<C>(context, trigger)", "org.kodein.di"))
inline fun <reified C : Any> DIAware.on(context: C, trigger: DITrigger? = this.diTrigger) = On(diContext(context), trigger)

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param getContext A function that gets the new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("on<C>(trigger, getContext)", "org.kodein.di"))
inline fun <reified C : Any> DIAware.on(trigger: DITrigger? = this.diTrigger, crossinline getContext: () -> C) = On(diContext(getContext), trigger)

/**
 * Allows to create a new DI object with a trigger set.
 *
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("on(trigger)", "org.kodein.di"))
fun DIAware.on(trigger: DITrigger?) = On(diContext, trigger)


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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("factory<A, T>(tag)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DirectDI.factory(tag: Any? = null) = Factory<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allFactories<A, T>(tag)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DirectDI.allFactories(tag: Any? = null) = AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<T>(tag)", "org.kodein.di"))
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = directDI.AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag).map { it.toProvider(fArg) }

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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag)", "org.kodein.di"))
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
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
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DirectDIAware.allInstances(tag: Any? = null, arg: Typed<A>) = directDI.AllFactories<A, T>(arg.type, org.kodein.type.generic(), tag).map { it.invoke(arg.value) }


/**
 * Allows to define a binding that will be called for any subtype of this type.
 *
 * First part of the `bind<Type>().subTypes() with { type -> binding }` syntax.
 *
 * @param T The parent type.
 * @param block A function that will give the binding for the provided sub-type.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("with<C, A, T>(block)", "org.kodein.di"))
inline infix fun <reified C : Any, reified A : Any, reified T: Any> TypeBinderSubTypes<T>.with(noinline block: (TypeToken<out T>) -> DIBinding<in C, in A, out T>) = With(org.kodein.type.generic(), org.kodein.type.generic(), org.kodein.type.generic(), block)
