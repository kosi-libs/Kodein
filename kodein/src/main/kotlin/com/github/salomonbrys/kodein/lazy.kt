package com.github.salomonbrys.kodein


interface LazyKodeinAwareBase {
    val kodein: LazyKodein
}

/**
 * An object that wraps a Kodein [Lazy] object and acts both as a [Lazy] property delegate & a function.
 *
 * @param k The property delegate to wrap.
 */
class LazyKodein(k: Lazy<Kodein>) : Lazy<Kodein> by k, () -> Kodein, LazyKodeinAwareBase {
    override val kodein: LazyKodein get() = this

    override fun invoke(): Kodein = value
}

/**
 * You can use the result of this function as a property delegate *or* as a function.
 *
 * @param f The function to get a Kodein, guaranteed to be called only once.
 */
fun lazyKodein(f: () -> Kodein) = LazyKodein(lazy(f))


class CurriedLazyKodeinFactory<A>(val kodein: () -> Kodein, val arg: A, val argType: TypeToken<A>) {

    inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T> = lazy { kodein().typed.factory(argType, typeToken<T>(), tag) } .toProvider(arg)

    inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<(() -> T)?> = lazy { kodein().typed.factoryOrNull(argType, typeToken<T>(), tag) } .toProvider(arg)

    inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T> = lazy { kodein().typed.factory(argType, typeToken<T>(), tag) } .toInstance(arg)

    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?> = lazy { kodein().typed.factoryOrNull(argType, typeToken<T>(), tag) } .toInstance(arg)
}



inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factory(tag: Any? = null) : Lazy<(A) -> T> = lazy { kodein().factory<A, T>(tag) }

inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null) : Lazy<((A) -> T)?> = lazy { kodein().factoryOrNull<A, T>(tag) }

inline fun <reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null) : Lazy<() -> T> = lazy { kodein().provider<T>(tag) }

inline fun <reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null) : Lazy<(() -> T)?> = lazy { kodein().providerOrNull<T>(tag) }

inline fun <reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null) : Lazy<T> = lazy { kodein().instance<T>(tag) }

inline fun <reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null) : Lazy<T?> = lazy { kodein().instanceOrNull<T>(tag) }

inline fun <reified A> LazyKodeinAwareBase.with(arg: A): CurriedLazyKodeinFactory<A> = CurriedLazyKodeinFactory(kodein, arg, typeToken())



interface LazyKodeinAware : LazyKodeinAwareBase



fun <A, T : Any> Lazy<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }

@JvmName("toNullableProvider")
fun <A, T : Any> Lazy<((A) -> T)?>.toProvider(arg: A): Lazy<(() -> T)?> = lazy { val factory = value ; if (factory != null) return@lazy { factory(arg) } else return@lazy null }

fun <A, T : Any> Lazy<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { value(arg) }

@JvmName("toNullableInstance")
fun <A, T : Any> Lazy<((A) -> T)?>.toInstance(arg: A): Lazy<T?> = lazy { val factory = value ; if (factory != null) factory(arg) else null }
