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
 * Allows to get a provider or an instance from a factory with a curried argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument that the factory takes.
 * @property kodein The Kodein instance to use for retrieval.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedKodeinFactory<A>(val kodein: () -> Kodein, val arg: () -> A, val argType: TypeToken<A>)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
fun <T : Any> CurriedKodeinFactory<*>.Provider(type: TypeToken<T>, tag: Any? = null): () -> T = kodein().Factory(argType, type, tag).toProvider(arg)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
fun <T : Any> CurriedKodeinFactory<*>.ProviderOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)? = kodein().FactoryOrNull(argType, type, tag)?.toProvider(arg)

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
fun <T : Any> CurriedKodeinFactory<*>.Instance(type: TypeToken<T>, tag: Any? = null): T = kodein().Factory(argType, type, tag).invoke(arg())

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
fun <T : Any> CurriedKodeinFactory<*>.InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T? = kodein().FactoryOrNull(argType, type, tag)?.invoke(arg())

//internal fun <A> KodeinAwareBase._With(argType: TypeToken<A>, arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, arg, argType)

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
fun <A> KodeinAwareBase.WithF(argType: TypeToken<A>, arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, arg, argType)

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
fun <A> KodeinAwareBase.With(argType: TypeToken<A>, arg: A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, { arg }, argType)

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
