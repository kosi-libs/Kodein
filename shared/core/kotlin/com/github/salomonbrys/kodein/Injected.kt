package com.github.salomonbrys.kodein

import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil.transform
import kotlin.reflect.KProperty

/**
 * Base [KodeinInjected] interface.
 *
 * It is separate from [KodeinInjected] because [KodeinInjector] implements itself [KodeinInjectedBase] but not [KodeinInjected].<br />
 * This is because there are some extension functions to [KodeinInjected] that would not make sense applied to the [KodeinInjector] object.<br />
 * For example, [KodeinInjected.withClass], if applied to [KodeinInjector], would create a very un-expected result.
 */
interface KodeinInjectedBase {

    /**
     * A Kodein Injected class must be within reach of a Kodein Injector object.
     */
    val injector: KodeinInjector

    /**
     * Will inject all properties that were created with the [injector] with the values found in the provided Kodein object.
     *
     * Will also call all callbacks that were registered with [onInjected].
     *
     * @param kodein The kodein object to use to inject all properties.
     * @throws Kodein.NotFoundException if one the properties is requesting a value that is not bound.
     * @throws Kodein.DependencyLoopException if one the instance properties is requesting a value whose construction triggered a dependency loop.
     */
    fun inject(kodein: Kodein): Unit = injector.inject(kodein)

    /**
     * Registers a callback to be called once the [injector] gets injected with a [Kodein] object.
     *
     * If the injector has already been injected, the callback will be called instantly.
     *
     * The callback is guaranteed to be called only once.
     *
     * @param cb The callback to register
     */
    fun onInjected(cb: (Kodein) -> Unit): Unit = injector.onInjected(cb)
}

/**
 * Gets a lazy [Kodein] object.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @return A lazy property that yields a [Kodein].
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
fun KodeinInjectedBase.kodein(): Lazy<Kodein> = injector.kodein()


interface LazyProvider<out T> {
    operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): Lazy<T>
}

@PublishedApi
internal fun <F, T> KodeinInjector.InjectedPropertyProvider<F>.toLazy(transform: InjectedProperty<F>.() -> T) = object : LazyProvider<T> {
    override fun provideDelegate(thisRef: Any?, prop: KProperty<*>) = this@toLazy.provideDelegate(thisRef, prop).let { lazy { it.transform() } }
}


/**
 * Transforms an injected factory property into an injected provider property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected provider property that, when called, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> KodeinInjector.InjectedPropertyProvider<(A) -> T>.toProvider(crossinline arg: () -> A): LazyProvider<() -> T> = toLazy { { value(arg()) } }

/**
 * Transforms an injected nullable factory property into an injected nullable provider property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected provider property that, when called, will call the receiver factory (if not null) with the given argument.
 */
inline fun <A, T : Any> KodeinInjector.InjectedPropertyProvider<((A) -> T)?>.toProviderOrNull(crossinline arg: () -> A): LazyProvider<(() -> T)?> = toLazy {
    val v = value ?: return@toLazy null
    return@toLazy { v(arg()) }
}

/**
 * Transforms an injected factory property into an injected instance property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected instance property that, when injected, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> KodeinInjector.InjectedPropertyProvider<(A) -> T>.toInstance(crossinline arg: () -> A): LazyProvider<T> = toLazy { value(arg()) }

/**
 * Transforms an injected factory property into an injected instance property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected instance property that, when injected, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> KodeinInjector.InjectedPropertyProvider<((A) -> T)?>.toInstanceOrNull(crossinline arg: () -> A): LazyProvider<T?> = toLazy {
    val v = value ?: return@toLazy null
    return@toLazy v(arg())
}



/**
 * Used to inject lazy providers or instances for factory bound types.
 *
 * @param A The type of argument that the factory takes.
 * @property injector The injector to use for injections.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedInjectorFactory<A>(val injector: KodeinInjector, val arg: () -> A, val argType: TypeToken<A>)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> CurriedInjectorFactory<*>.Provider(type: TypeToken<T>, tag: Any? = null): LazyProvider<() -> T> = injector.Factory(argType, type, tag).toProvider(arg)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> CurriedInjectorFactory<*>.ProviderOrNull(type: TypeToken<T>, tag: Any? = null): LazyProvider<(() -> T)?> = injector.FactoryOrNull(argType, type, tag).toProviderOrNull(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
fun <T : Any> CurriedInjectorFactory<*>.Instance(type: TypeToken<T>, tag: Any? = null): LazyProvider<T> = injector.Factory(argType, type, tag).toInstance(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
fun <T : Any> CurriedInjectorFactory<*>.InstanceOrNull(type: TypeToken<T>, tag: Any? = null): LazyProvider<T?> = injector.FactoryOrNull(argType, type, tag).toInstanceOrNull(arg)

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
fun <A> KodeinInjectedBase.WithF(argType: TypeToken<A>, arg: () -> A) = CurriedInjectorFactory(injector, arg, argType)

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg The argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
fun <A> KodeinInjectedBase.With(argType: TypeToken<A>, arg: A): CurriedInjectorFactory<A> = CurriedInjectorFactory(injector, { arg }, argType)



/**
 * Any class that extends this interface can be injected "seamlessly".
 */
interface KodeinInjected : KodeinInjectedBase
