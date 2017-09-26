package com.github.salomonbrys.kodein

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
 * Any class that extends this interface can be injected "seamlessly".
 */
interface KodeinInjected : KodeinInjectedBase
