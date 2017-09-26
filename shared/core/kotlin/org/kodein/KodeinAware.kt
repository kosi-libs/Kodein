package org.kodein

/**
 * Base [KodeinAware] interface.
 *
 * It is separate from [KodeinAware] because [Kodein] implements itself [KodeinAwareBase] but not [KodeinAware].<br />
 * This is because there are some extension functions to [KodeinAware] that would not make sense applied to the [Kodein] object.<br />
 * For example, [KodeinAware.withClass], if applied to [Kodein], would create a very un-expected result.
 */
interface KodeinAwareBase {

    /**
     * A Kodein Aware class must be within reach of a Kodein object.
     */
    val kodein: Kodein
}

@PublishedApi
internal val KodeinAwareBase._receiver get() = if (this !is Kodein) this else null

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
fun <A, T : Any> KodeinAwareBase.Factory(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null) = kodein.Factory(argType, type, tag, _receiver)

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
fun <A, T : Any> KodeinAwareBase.FactoryOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)? = kodein.FactoryOrNull(argType, type, tag, _receiver)

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
fun <T : Any> KodeinAwareBase.Provider(type: TypeToken<T>, tag: Any? = null): () -> T = kodein.Provider(type, tag, _receiver)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAwareBase.ProviderOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)? = kodein.ProviderOrNull(type, tag, _receiver)

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
fun <T : Any> KodeinAwareBase.Instance(type: TypeToken<T>, tag: Any? = null): T = kodein.Instance(type, tag, _receiver)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAwareBase.InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T? = kodein.InstanceOrNull(type, tag, _receiver)


/**
 * Allows to create a new instance of an unbound object with the same API as when bounding one.
 *
 * @param T The type of object to create.
 * @param creator A function that do create the object.
 */
inline fun <T> KodeinAwareBase.newInstance(creator: Kodein.() -> T): T = kodein.run(creator)


/**
 * Any class that extends this interface can use Kodein "seamlessly".
 */
interface KodeinAware : KodeinAwareBase
