package com.github.salomonbrys.kodein

class LazyKodein(private val _lazy: Lazy<Kodein>) : () -> Kodein {
    override fun invoke() = _lazy.value

    constructor(silentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(lazy { Kodein(silentOverride, init) })
}

inline fun <reified A, reified T : Any> _lazyFactory(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<(A) -> T> = lazy { kodein().factory<A, T>(tag) }
inline fun <reified T : Any>            _lazyProvider(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<() -> T> = lazy { kodein().provider<T>(tag) }
inline fun <reified T : Any>            _lazyInstance(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<T>       = lazy { kodein().instance<T>(tag) }



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> Kodein.lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> Kodein.lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> Kodein.lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this }

inline fun <reified A, reified T : Any> Kodein.lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>().toLazyProvider(arg)

inline fun <reified A, reified T : Any> Kodein.lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>().toLazyInstance(arg)



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this.value }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> Lazy<Kodein>.lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this.value }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> Lazy<Kodein>.lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this.value }

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>().toLazyProvider(arg)

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>().toLazyInstance(arg)



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> (() -> Kodein).lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this() }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> (() -> Kodein).lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this() }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> (() -> Kodein).lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this() }

inline fun <reified A, reified T : Any> (() -> Kodein).lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>().toLazyProvider(arg)

inline fun <reified A, reified T : Any> (() -> Kodein).lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>().toLazyInstance(arg)



fun <A, T : Any> Lazy<(A) -> T>.toLazyProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }

fun <A, T : Any> Lazy<(A) -> T>.toLazyInstance(arg: A): Lazy<T> = lazy { value(arg) }
