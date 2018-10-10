package org.kodein.di

/**
 * Any class that extends this interface can use direct Kodein "seamlessly".
 */
interface DKodeinAware {
    /**
     * A Direct Kodein Aware class must be within reach of a [DKodein] object.
     */
    val dkodein: DKodein
}

/**
 * @see [DKodein]
 */
@Suppress("FunctionName", "ClassName")
interface DKodeinBase : DKodeinAware {

    /**
     * Every methods eventually ends up to a call to this container.
     */
    val container: KodeinContainer

    /**
     * Returns a regular [Kodein] instance (Kodein is lazy by default).
     */
    val lazy: Kodein

    /**
     * Returns a regular [Kodein] instance (Kodein is lazy by default).
     */
    val kodein: Kodein get() = lazy

    /** @suppress */
    object SAME_CONTEXT : KodeinContext<Unit>(UnitToken, Unit)

    /** @suppress */
    object SAME_RECEIVER

    /**
     * Returns a [DKodein] with its context and/or receiver changed.
     *
     * @param context The new context for the new DKodein.
     * @param receiver The new receiver for the new DKodein.
     */
    fun On(context: KodeinContext<*> = SAME_CONTEXT, receiver: Any? = SAME_RECEIVER): DKodein

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null): (A) -> T

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)?

    /**
     * Gets a provider of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <T : Any> Provider(type: TypeToken<T>, tag: Any? = null): () -> T

    /**
     * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument type.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A provider of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): () -> T

    /**
     * Gets a provider of `T` for the given type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)?

    /**
     * Gets a provider of `T` for the given type and tag, curried from a factory for the given argument type, or null if none is found.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A provider of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): (() -> T)?

    /**
     * Gets an instance of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve.
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    fun <T : Any> Instance(type: TypeToken<T>, tag: Any? = null): T

    /**
     * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument type.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg The argument that will be given to the factory when curried.
     * @return An instance of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: A): T

    /**
     * Gets an instance of `T` for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T?

    /**
     * Gets an instance of `T` for the given type and tag, curried from a factory for the given argument type, or null if none is found.
     *
     * @param A The type of argument the curried factory takes.
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @param arg A function that returns the argument that will be given to the factory when curried.
     * @return A instance of `T`, or null if no provider was found.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: A): T?
}

/**
 * D stands for Direct. Direct Kodein!
 *
 * Acts like a [Kodein] object but returns factories, providers and instances instead of returning property delegates.
 * In essence, a DKodein is used with `=` instead of with `by`.
 *
 * Note that `DKodein` is engineered to also work with Java code.
 */
expect interface DKodein : DKodeinBase

/**
 * Allows the creation of a new instance with Kodein injection.
 */
inline fun <T> DKodeinAware.newInstance(creator: DKodein.() -> T): T = dkodein.run(creator)

/**
 * Returns a regular [Kodein] instance (Kodein is lazy by default).
 */
val DKodeinAware.lazy: Kodein get() = dkodein.lazy
