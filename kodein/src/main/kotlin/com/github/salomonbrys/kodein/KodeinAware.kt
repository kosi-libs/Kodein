package com.github.salomonbrys.kodein

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

/**
 * Gets a factory of [T] for the given argument type, return type and tag.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factory(tag: Any? = null): (A) -> T = kodein.typed.factory(typeToken<A>(), typeToken<T>(), tag)

/**
 * Gets a factory of [T] for the given argument type, return type and tag, or nul if none is found.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factoryOrNull(tag: Any? = null): ((A) -> T)? = kodein.typed.factoryOrNull(typeToken<A>(), typeToken<T>(), tag)

/**
 * Gets a provider of [T] for the given type and tag.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.provider(tag: Any? = null): () -> T = kodein.typed.provider(typeToken<T>(), tag)

/**
 * Gets a provider of [T] for the given type and tag, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.providerOrNull(typeToken<T>(), tag)

/**
 * Gets an instance of [T] for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.instance(tag: Any? = null): T = kodein.typed.instance(typeToken<T>(), tag)

/**
 * Gets an instance of [T] for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null): T? = kodein.typed.instanceOrNull(typeToken<T>(), tag)

/**
 * Allows to get a provider or an instance from a factory with a curried argument.
 *
 * @param A The type of argument that the factory takes.
 * @property kodein The Kodein instance to use for retrieval.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedKodeinFactory<A>(val kodein: Kodein, val arg: () -> A, val argType: TypeToken<A>) {

    /**
     * Gets a provider of [T] for the given tag from a curried factory with an [A] argument.
     *
     * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
     *
     * @param T The type of object the factory returns.
     * @param tag The bound tag, if any.
     * @return A provider.
     * @throws Kodein.NotFoundException if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    inline fun <reified T : Any> provider(tag: Any? = null): () -> T = kodein.typed.factory(argType, typeToken<T>(), tag).toProvider(arg)

    /**
     * Gets a provider of [T] for the given tag from a curried factory with an [A] argument, or null if none is found.
     *
     * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
     *
     * @param T The type of object the factory returns.
     * @param tag The bound tag, if any.
     * @return A provider, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.toProvider(arg)

    /**
     * Gets an instance of [T] for the given tag from a curried factory with an [A] argument.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     *
     * @param T The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance.
     * @throws Kodein.NotFoundException if no factory was found.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    inline fun <reified T : Any> instance(tag: Any? = null): T = kodein.typed.factory(argType, typeToken<T>(), tag).invoke(arg())

    /**
     * Gets an instance of [T] for the given tag from a curried factory with an [A] argument, or null if none is found.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     *
     * @param T The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance, or null if no factory was found.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.invoke(arg())
}

/**
 * Allows to get a provider or an instance from a curried factory with an [A] argument.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.with(noinline arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory(kodein, arg, typeToken())

/**
 * Allows to get a provider or an instance from a curried factory with an [A] argument.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.with(arg: A): CurriedKodeinFactory<A> = with { arg }

/**
 * Any class that extends this interface can use Kodein "seemlessly".
 */
interface KodeinAware : KodeinAwareBase
