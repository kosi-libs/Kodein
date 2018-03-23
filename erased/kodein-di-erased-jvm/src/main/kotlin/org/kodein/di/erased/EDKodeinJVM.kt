package org.kodein.di.erased

import org.kodein.di.*

/**
 * Gets all factories that can return a `T` for the given argument type, return type and tag.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching factories of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allFactories(tag: Any? = null) = dkodein.AllFactories<A, T>(erased(), erased(), tag)

/**
 * Gets all providers that can return a `T` for the given type and tag.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching providers of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.allProviders(tag: Any? = null) = dkodein.AllProviders<T>(erased(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, arg: A) = dkodein.AllProviders<A, T>(erased(), erased(), tag) { arg }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, arg: Typed<A>) = dkodein.AllProviders<A, T>(arg.type, erased(), tag) { arg.value }

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = dkodein.AllProviders<A, T>(erased(), erased(), tag, fArg)

/**
 * Gets all instances that can return a `T` for the given type and tag.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A list of matching instances of `T`.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.allInstances(tag: Any? = null) = dkodein.AllInstances<T>(erased(), tag)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.allInstances(tag: Any? = null, arg: A) = dkodein.AllInstances<A, T>(erased(), erased(), tag, arg)

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
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> DKodeinAware.allInstances(tag: Any? = null, arg: Typed<A>) = dkodein.AllInstances<A, T>(arg.type, erased(), tag, arg.value)

