package com.github.salomonbrys.kodein

class LazyKodein(private val _lazy: Lazy<Kodein>) : () -> Kodein {
    override fun invoke() = _lazy.value

    constructor(silentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(lazy { Kodein(silentOverride, init) })
}

inline fun <reified A, reified T : Any> _lazyFactory(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<(A) -> T>          = lazy { kodein().factory<A, T>(tag) }
inline fun <reified A, reified T : Any> _lazyFactoryOrNull(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<((A) -> T)?> = lazy { kodein().factoryOrNull<A, T>(tag) }
inline fun <reified T : Any>            _lazyProvider(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<() -> T>          = lazy { kodein().provider<T>(tag) }
inline fun <reified T : Any>            _lazyProviderOrNull(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<(() -> T)?> = lazy { kodein().providerOrNull<T>(tag) }
inline fun <reified T : Any>            _lazyInstance(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<T>                = lazy { kodein().instance<T>(tag) }
inline fun <reified T : Any>            _lazyInstanceOrNull(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<T?>         = lazy { kodein().instanceOrNull<T>(tag) }



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> Kodein.lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this }

inline fun <reified A, reified T : Any> Kodein.lazyFactoryOrNull(tag: Any? = null) : Lazy<((A) -> T)?> = _lazyFactoryOrNull(tag) { this }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> Kodein.lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this }

inline fun <reified T : Any> Kodein.lazyProviderOrNull(tag: Any? = null) : Lazy<(() -> T)?> = _lazyProviderOrNull(tag) { this }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> Kodein.lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this }

inline fun <reified T : Any> Kodein.lazyInstanceOrNull(tag: Any? = null) : Lazy<T?> = _lazyInstanceOrNull(tag) { this }

inline fun <reified A, reified T : Any> Kodein.lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> Kodein.lazyProviderFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<(() -> T)?> = lazyFactoryOrNull<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> Kodein.lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>(tag).toLazyInstance(arg)

inline fun <reified A, reified T : Any> Kodein.lazyInstanceFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<T?> = lazyFactoryOrNull<A, T>(tag).toLazyInstance(arg)



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this.value }

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyFactoryOrNull(tag: Any? = null) : Lazy<((A) -> T)?> = _lazyFactoryOrNull(tag) { this.value }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> Lazy<Kodein>.lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this.value }

inline fun <reified T : Any> Lazy<Kodein>.lazyProviderOrNull(tag: Any? = null) : Lazy<(() -> T)?> = _lazyProviderOrNull(tag) { this.value }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> Lazy<Kodein>.lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this.value }

inline fun <reified T : Any> Lazy<Kodein>.lazyInstanceOrNull(tag: Any? = null) : Lazy<T?> = _lazyInstanceOrNull(tag) { this.value }

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyProviderFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<(() -> T)?> = lazyFactoryOrNull<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>(tag).toLazyInstance(arg)

inline fun <reified A, reified T : Any> Lazy<Kodein>.lazyInstanceFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<T?> = lazyFactoryOrNull<A, T>(tag).toLazyInstance(arg)



/**
 * To be used as a property delegate to inject a factory
 */
inline fun <reified A, reified T : Any> (() -> Kodein).lazyFactory(tag: Any? = null) : Lazy<(A) -> T> = _lazyFactory(tag) { this() }

inline fun <reified A, reified T : Any> (() -> Kodein).lazyFactoryOrNull(tag: Any? = null) : Lazy<((A) -> T)?> = _lazyFactoryOrNull(tag) { this() }

/**
 * To be used as a property delegate to inject a provider
 */
inline fun <reified T : Any> (() -> Kodein).lazyProvider(tag: Any? = null) : Lazy<() -> T> = _lazyProvider(tag) { this() }

inline fun <reified T : Any> (() -> Kodein).lazyProviderOrNull(tag: Any? = null) : Lazy<(() -> T)?> = _lazyProviderOrNull(tag) { this() }

/**
 * To be used as a property delegate to inject an instance
 */
inline fun <reified T : Any> (() -> Kodein).lazyInstance(tag: Any? = null) : Lazy<T> = _lazyInstance(tag) { this() }

inline fun <reified T : Any> (() -> Kodein).lazyInstanceOrNull(tag: Any? = null) : Lazy<T?> = _lazyInstanceOrNull(tag) { this() }

inline fun <reified A, reified T : Any> (() -> Kodein).lazyProviderFromFactory(arg: A, tag: Any? = null) : Lazy<() -> T> = lazyFactory<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> (() -> Kodein).lazyProviderFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<(() -> T)?> = lazyFactoryOrNull<A, T>(tag).toLazyProvider(arg)

inline fun <reified A, reified T : Any> (() -> Kodein).lazyInstanceFromFactory(arg: A, tag: Any? = null) : Lazy<T> = lazyFactory<A, T>(tag).toLazyInstance(arg)

inline fun <reified A, reified T : Any> (() -> Kodein).lazyInstanceFromFactoryOrNull(arg: A, tag: Any? = null) : Lazy<T?> = lazyFactoryOrNull<A, T>(tag).toLazyInstance(arg)



inline fun <A, reified T : Any> CurriedKodeinFactory<A>.lazyProvider(tag: Any? = null): Lazy<() -> T> = lazy { provider<T>(tag) }

inline fun <A, reified T : Any> CurriedKodeinFactory<A>.lazyProviderOrNull(tag: Any? = null): Lazy<(() -> T)?> = lazy { providerOrNull<T>(tag) }

inline fun <A, reified T : Any> CurriedKodeinFactory<A>.lazyInstance(tag: Any? = null): Lazy<T> = lazy { instance<T>(tag) }

inline fun <A, reified T : Any> CurriedKodeinFactory<A>.lazyInstanceOrNull(tag: Any? = null): Lazy<T?> = lazy { instanceOrNull<T>(tag) }



fun <A, T : Any> Lazy<(A) -> T>.toLazyProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }

@JvmName("toLazyNullableProvider")
fun <A, T : Any> Lazy<((A) -> T)?>.toLazyProvider(arg: A): Lazy<(() -> T)?> = lazy { val factory = value ; if (factory != null) return@lazy { factory(arg) } else return@lazy null }

fun <A, T : Any> Lazy<(A) -> T>.toLazyInstance(arg: A): Lazy<T> = lazy { value(arg) }

@JvmName("toLazyNullableInstance")
fun <A, T : Any> Lazy<((A) -> T)?>.toLazyInstance(arg: A): Lazy<T?> = lazy { val factory = value ; if (factory != null) factory(arg) else null }
