package org.kodein.di.erased

import org.kodein.di.*

/**
 * Gets all factories that match the the given argument type, return type and tag.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factories take.
 * @param T The type of object to retrieve with the factories.
 * @param tag The bound tag, if any.
 * @return A list of factories of [T].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.allFactories(tag: Any? = null) = AllFactories<A, T>(erased(), erased(), tag)

/**
 * Gets all providers that match the the given return type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of providers of [T].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.allProviders(tag: Any? = null) = AllProviders<T>(erased(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.allProviders(tag: Any? = null, arg: A) = AllProviders<A, T>(erased(), erased(), tag) { arg }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.allProviders(tag: Any? = null, arg: Typed<A>) = AllProviders<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = AllProviders<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets all instances from providers that match the the given return type and tag.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of [T] instances.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAware.allInstances(tag: Any? = null) = AllInstances<T>(erased(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.allInstances(tag: Any? = null, arg: A) = AllInstances<A, T>(erased(), erased(), tag) { arg }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> KodeinAware.allInstances(tag: Any? = null, arg: Typed<A>) = AllInstances<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAware.allInstances(tag: Any? = null, noinline fArg: () -> A) = AllInstances<A, T>(erased(), erased(), tag, fArg)
