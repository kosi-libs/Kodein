package org.kodein.di

import org.kodein.type.TypeToken

@Suppress("FunctionName")
public actual interface DirectDI : DirectDIBase {
    /**
     * Gets all factories that can return a `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A list of matching factories of `T`.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> AllFactories(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null): List<(A) -> T>

    /**
     * Gets all providers that can return a `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A list of matching providers of `T`.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <T : Any> AllProviders(type: TypeToken<T>, tag: Any? = null): List<() -> T>

    /**
     * Gets all providers that can return a `T` for the given type and tag, curried from factories for the given argument type.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A list of matching providers of `T`.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> AllProviders(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): List<() -> T>

    /**
     * Gets all instances that can return a `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A list of matching instances of `T`.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <T : Any> AllInstances(type: TypeToken<T>, tag: Any? = null): List<T>

    /**
     * Gets all instances that can return a `T` for the given type and tag, curried from factories for the given argument type.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A list of matching instances of `T`.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> AllInstances(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: A): List<T>
}