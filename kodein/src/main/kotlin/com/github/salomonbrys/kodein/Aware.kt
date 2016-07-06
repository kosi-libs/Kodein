package com.github.salomonbrys.kodein

/**
 * Base [KodeinAware] interface.
 *
 * It is separate from [KodeinAware] because [Kodein] implements itself [KodeinAwareBase] but not [KodeinAware].<br />
 * This is because there are some extension functions to [KodeinAware] that would not make sense applied to the [Kodein] object.<br />
 * For example, [KodeinAware.instanceForClass], if applied to [Kodein], would create a very un-expected result.
 */
interface KodeinAwareBase {

    /**
     * A Kodein Aware class must be within reach of a Kodein object.
     */
    val kodein: Kodein
}

/**
 * Gets a factory for the given argument type, return type and tag.
 *
 * @return The factory.
 * @throws Kodein.NotFoundException if no factory could be found for this type and tag.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factory(tag: Any? = null): (A) -> T = kodein.typed.factory(typeToken<A>(), typeToken<T>(), tag)

/**
 * gets a factory for the given argument type, return type and tag, or null if non is found.
 *
 * @return the factory, or null if non could be found.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factoryOrNull(tag: Any? = null): ((A) -> T)? = kodein.typed.factoryOrNull(typeToken<A>(), typeToken<T>(), tag)

/**
 * Gets a provider for the given type and tag.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * @return The provider.
 * @throws Kodein.NotFoundException if no provider could be found for this type and tag.
 */
inline fun <reified T : Any> KodeinAwareBase.provider(tag: Any? = null): () -> T = kodein.typed.provider(typeToken<T>(), tag)

/**
 * Gets a provider for the given type and tag, or null if none is found.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * @return The provider, or null if none could be found.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.providerOrNull(typeToken<T>(), tag)

/**
 * Gets an instance for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @return The instance.
 * @throws Kodein.NotFoundException if no provider or factory could be found for this type and tag.
 */
inline fun <reified T : Any> KodeinAwareBase.instance(tag: Any? = null): T = kodein.typed.instance(typeToken<T>(), tag)

/**
 * Gets an instance for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @return The instance, or null if no provider or factory could be found.
 */
inline fun <reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null): T? = kodein.typed.instanceOrNull(typeToken<T>(), tag)

/**
 * Allows to get a provider or an instance from a factory with a curried argument.
 */
class CurriedKodeinFactory<A>(val kodein: Kodein, val arg: A, val argType: TypeToken<A>) {

    /**
     * Gets a provider from the factory bound for the given type, tag and curried argument type.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     *
     * @return The provider.
     * @throws Kodein.NotFoundException if no factory could be found..
     */
    inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = kodein.typed.factory(argType, typeToken<T>(), tag).toProvider(arg)

    /**
     * Gets a provider from the factory bound for the given type, tag and curried argument type.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     *
     * @return The provider, or null if no factory could be found.
     */
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.toProvider(arg)

    /**
     * Gets an instance from the factory bound for the given type, tag and curried argument type.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     *
     * @return The instance.
     * @throws Kodein.NotFoundException if no factory could be found.
     */
    inline fun <reified T : Any> instance(tag: Any? = null): T = kodein.typed.factory(argType, typeToken<T>(), tag).invoke(arg)

    /**
     * Gets an instance from the factory bound for the given type, tag and curried argument type.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     *
     * @return The instance, or null if no factory could be found
     */
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.invoke(arg)
}

/**
 * Allows to get a provider or an instance from a factory with a curried argument.
 */
inline fun <reified A> KodeinAwareBase.with(arg: A) = CurriedKodeinFactory(kodein, arg, typeToken<A>())

/**
 * Finds a factory for the given type, tag and argument type, and returns a curried provider with the given argument.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.

 * @return The provider.
 * @throws Kodein.NotFoundException if no factory could be found..
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.providerFromFactory(arg: A, tag: Any? = null): () -> T = factory<A, T>(tag).toProvider(arg)

/**
 * Gets a provider from the factory bound for the given type, tag and curried argument type.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * @return The provider, or null if no factory could be found.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.providerFromFactoryOrNull(arg: A, tag: Any? = null): (() -> T)? = factoryOrNull<A, T>(tag)?.toProvider(arg)

/**
 * Finds a factory for the given type, tag and argument type, and returns an instance by invoking this instance with the given argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @return The instance.
 * @throws Kodein.NotFoundException if no factory could be found..
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.instanceFromFactory(arg: A, tag: Any? = null): T = factory<A, T>(tag).invoke(arg)

/**
 * Finds a factory for the given type, tag and argument type, and returns an instance by invoking this instance with the given argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @return The instance, or null if no factory could be found.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.instanceFromFactoryOrNull(arg: A, tag: Any? = null): T? = factoryOrNull<A, T>(tag)?.invoke(arg)


/**
 * Any class that extends this interface can use Kotlin "seemlessly".
 */
interface KodeinAware : KodeinAwareBase
