package com.github.salomonbrys.kodein

import javax.sound.midi.MidiDeviceReceiver

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
 * Allows to get a provider or an instance from a factory with a curried argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument that the factory takes.
 * @property kodein The Kodein instance to use for retrieval.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedKodeinFactory<A>(val kodein: () -> Kodein, val arg: () -> A, val argType: TypeToken<A>, val receiver: Any? /*= null*/)

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
fun <T : Any> CurriedKodeinFactory<*>.Provider(type: TypeToken<T>, tag: Any? = null): () -> T = kodein().Factory(argType, type, tag, receiver).toProvider(arg)

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
fun <T : Any> CurriedKodeinFactory<*>.ProviderOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)? = kodein().FactoryOrNull(argType, type, tag, null)?.toProvider(arg)

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
fun <T : Any> CurriedKodeinFactory<*>.Instance(type: TypeToken<T>, tag: Any? = null): T = kodein().Factory(argType, type, tag, receiver).invoke(arg())

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
fun <T : Any> CurriedKodeinFactory<*>.InstanceOrNull(type: TypeToken<T>, tag: Any? = null): T? = kodein().FactoryOrNull(argType, type, tag, receiver)?.invoke(arg())

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
fun <A> KodeinAwareBase.WithF(argType: TypeToken<A>, arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, arg, argType, this)

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
fun <A> KodeinAwareBase.With(argType: TypeToken<A>, arg: A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, { arg }, argType, this)

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
