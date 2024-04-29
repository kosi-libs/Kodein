package org.kodein.di

import org.kodein.type.TypeToken

/**
 * Any class that extends this interface can use direct DI "seamlessly".
 */
public interface DirectDIAware {
    /**
     * A Direct DI Aware class must be within reach of a [DirectDI] object.
     */
    public val directDI: DirectDI
}

/**
 * @see [DirectDI]
 */
@Suppress("FunctionName")
public interface DirectDIBase : DirectDIAware {

    /**
     * Every methods eventually ends up to a call to this container.
     */
    public val container: DIContainer

    /**
     * Returns a regular [DI] instance (DI is lazy by default).
     */
    public val lazy: DI

    /**
     * Returns a regular [DI] instance (DI is lazy by default).
     */
    public val di: DI get() = lazy

    /**
     * Returns a [DirectDI] with its context changed.
     *
     * @param context The new context for the new DirectDI.
     */
    public fun On(context: DIContext<*>): DirectDI

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws DI.NotFoundException If no factory was found.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null): (A) -> T

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)?

    /**
     * Gets a provider of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <T : Any> Provider(type: TypeToken<T>, tag: Any? = null): () -> T

    /**
     * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument type.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A provider of `T`.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): () -> T

    /**
     * Gets a provider of `T` for the given type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`, or null if no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)?

    /**
     * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument type, or null if none is found.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A provider of `T`, or null if no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): (() -> T)?

    /**
     * Gets an instance of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve.
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
     */
    public fun <T : Any> Instance(type: TypeToken<T>, tag: Any? = null): T

    /**
     * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument type.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg The argument that will be given to the factory when curried.
     * @return An instance of `T`.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: A): T

    /**
     * Gets an instance of `T` for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`, or null if no provider was found.
     * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
     */
    public fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T?

    /**
     * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument type, or null if none is found.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A instance of `T`, or null if no provider was found.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    public fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: A): T?
}

/**
 * D stands for Direct. Direct DI!
 *
 * Acts like a [DI] object but returns factories, providers and instances instead of returning property delegates.
 * In essence, a DirectDI is used with `=` instead of with `by`.
 *
 * Note that `DirectDI` is engineered to also work with Java code.
 */
public expect interface DirectDI : DirectDIBase

/**
 * Allows the creation of a new instance with DI injection.
 */
public inline fun <T> DirectDIAware.newInstance(creator: DirectDI.() -> T): T = directDI.run(creator)

/**
 * Returns a regular [DI] instance (DI is lazy by default).
 */
public val DirectDIAware.lazy: DI get() = directDI.lazy
