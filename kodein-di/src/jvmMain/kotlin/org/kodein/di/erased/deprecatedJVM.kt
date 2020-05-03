package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.TypeBinderSubTypes
import org.kodein.di.generic.allFactories
import org.kodein.type.TypeToken

/**
 * Gets all factories that match the the given argument type, return type and tag.
 *
 * A & T generics will be erased!
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
 * Gets all providers that match the the given return type and tag.
 *
 * T generics will be erased!
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
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allProviders(tag: Any? = null, arg: A) = AllProviders<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag) { arg }

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.allProviders(tag: Any? = null, arg: Typed<A>) = AllProviders<A, T>(arg.type, org.kodein.type.generic(), tag) { arg.value }

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be erased!
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
 * Gets all instances from providers that match the the given return type and tag.
 *
 * T generics will be erased!
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
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DIAware.allInstances(tag: Any? = null, arg: A) = AllInstances<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag) { arg }

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DIAware.allInstances(tag: Any? = null, arg: Typed<A>) = AllInstances<A, T>(arg.type, org.kodein.type.generic(), tag) { arg.value }

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * A & T generics will be erased!
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
 * Gets all factories that can return a `T` for the given argument type, return type and tag.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching factories of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allFactories<A, T>(tag)", "org.kodein.di"))
inline fun <reified A: Any, reified T : Any> DirectDIAware.allFactories(tag: Any? = null) = directDI.AllFactories<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag)

/**
 * Gets all providers that can return a `T` for the given type and tag.
 *
 * T generics will be erased.
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
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<T>(tag, arg)", "org.kodein.di"))
inline fun <reified A : Any, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, arg: A) = directDI.AllProviders<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag) { arg }

/**
 * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, arg: Typed<A>) = directDI.AllProviders<A, T>(arg.type, org.kodein.type.generic(), tag) { arg.value }

/**
 * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching providers of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allProviders<A, T>(tag, fArg)", "org.kodein.di"))
inline fun <reified A: Any, reified T : Any> DirectDIAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = directDI.AllProviders<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)

/**
 * Gets all instances that can return a `T` for the given type and tag.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<T>(tag)", "org.kodein.di"))
inline fun <reified T : Any> DirectDIAware.allInstances(tag: Any? = null) = directDI.AllInstances<T>(org.kodein.type.generic(), tag)

/**
 * Gets all instances that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <reified A: Any, reified T : Any> DirectDIAware.allInstances(tag: Any? = null, arg: A) = directDI.AllInstances<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, arg)

/**
 * Gets all instances that can return a `T` for the given type and tag, curried from factories for the given argument.
 *
 * The argument type is extracted from the `Typed.type` of the argument.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of matching instances of `T`.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("allInstances<A, T>(tag, arg)", "org.kodein.di"))
inline fun <A, reified T : Any> DirectDIAware.allInstances(tag: Any? = null, arg: Typed<A>) = directDI.AllInstances<A, T>(arg.type, org.kodein.type.generic(), tag, arg.value)

/**
 * Allows to define a binding that will be called for any subtype of this type.
 *
 * First part of the `bind<Type>().subTypes() with { type -> binding }` syntax.
 *
 * @param T The parent type.
 * @param block A function that will give the binding for the provided sub-type.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("with<C, A, T>(block)", "org.kodein.di"))
inline infix fun <reified  C : Any, reified A : Any, reified T: Any> TypeBinderSubTypes<T>.with(noinline block: (TypeToken<out T>) -> DIBinding<in C, in A, out T>) = With<C, A>(org.kodein.type.generic(), org.kodein.type.generic(), org.kodein.type.generic(), block)
