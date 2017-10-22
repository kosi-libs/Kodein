package org.kodein

interface DKodein {

    val kodein: Kodein

    fun on(receiver: Any?): DKodein

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
    fun <A, T : Any> Factory(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null): (A) -> T

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
    fun <A, T : Any> FactoryOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)?

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
     * Gets an instance of `T` for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T?
}
