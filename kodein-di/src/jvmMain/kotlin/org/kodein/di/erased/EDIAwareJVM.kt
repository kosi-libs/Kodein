package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.generic.allFactories

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
inline fun <reified A : Any, reified T : Any> DIAware.allInstances(tag: Any? = null, noinline fArg: () -> A) = AllInstances<A, T>(org.kodein.type.generic(), org.kodein.type.generic(), tag, fArg)
