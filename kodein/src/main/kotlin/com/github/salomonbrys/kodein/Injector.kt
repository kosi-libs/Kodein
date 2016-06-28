package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*

@Suppress("unused")
class KodeinInjector() {

    class UninjectedException : RuntimeException("Value has not been injected")

    private val _list = LinkedList<Injected<*>>()

    private var _kodein: Kodein? = null

    private var _onInjecteds = ArrayList<(Kodein) -> Unit>()

    fun onInjected(cb: (Kodein) -> Unit) {
        val k1 = _kodein
        if (k1 != null)
            cb(k1)
        else
            synchronized(this@KodeinInjector) {
                val k2 = _kodein
                if (k2 != null)
                    cb(k2)
                else
                    _onInjecteds.add(cb)
            }
    }

    private fun <T> _register(injected: Injected<T>): Injected<T> {
        val k1 = _kodein
        if (k1 != null)
            injected._inject(k1._container)
        else
            synchronized(this@KodeinInjector) {
                val k2 = _kodein
                if (k2 != null)
                    injected._inject(k2._container)
                else
                    _list.add(injected)
            }

        return injected
    }

    @Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
    inner class TInjector {

        @JvmOverloads
        fun factory(argType: Type, type: Type, tag: Any? = null): Injected<(Any?) -> Any> = _register(InjectedFactory(Kodein.Key(Kodein.Bind(type, tag), argType)))

        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: Class<T>, tag: Any? = null): Injected<(Any?) -> T> = factory(argType, type as Type, tag) as Injected<(Any?) -> T>

        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: TypeToken<T>, tag: Any? = null): Injected<(Any?) -> T> = factory(argType, type.type, tag) as Injected<(Any?) -> T>

        @JvmOverloads
        fun <A> factory(argType: Class<A>, type: Type, tag: Any? = null): Injected<(A) -> Any> = factory(argType as Type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: Class<T>, tag: Any? = null): Injected<(A) -> T> = factory(argType as Type, type as Type, tag) as Injected<(A) -> T>

        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): Injected<(A) -> T> = factory(argType as Type, type.type, tag) as Injected<(A) -> T>

        @JvmOverloads
        fun <A> factory(argType: TypeToken<A>, type: Type, tag: Any? = null): Injected<(A) -> Any> = factory(argType.type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): Injected<(A) -> T> = factory(argType.type, type as Type, tag) as Injected<(A) -> T>

        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): Injected<(A) -> T> = factory(argType.type, type.type, tag) as Injected<(A) -> T>



        @JvmOverloads
        fun factoryOrNull(argType: Type, type: Type, tag: Any? = null): Injected<((Any?) -> Any)?> = _register(InjectedNullableFactory(Kodein.Key(Kodein.Bind(type, tag), argType)))

        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: Class<T>, tag: Any? = null): Injected<((Any?) -> T)?> = factoryOrNull(argType, type as Type, tag) as Injected<((Any?) -> T)?>

        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: TypeToken<T>, tag: Any? = null): Injected<((Any?) -> T)?> = factoryOrNull(argType, type.type, tag) as Injected<((Any?) -> T)?>

        @JvmOverloads
        fun <A> factoryOrNull(argType: Class<A>, type: Type, tag: Any? = null): Injected<((A) -> Any)?> = factoryOrNull(argType as Type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: Class<T>, tag: Any? = null): Injected<((A) -> T)?> = factoryOrNull(argType as Type, type as Type, tag) as Injected<((A) -> T)?>

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): Injected<((A) -> T)?> = factoryOrNull(argType as Type, type.type, tag) as Injected<((A) -> T)?>

        @JvmOverloads
        fun <A> factoryOrNull(argType: TypeToken<A>, type: Type, tag: Any? = null): Injected<((A) -> Any)?> = factoryOrNull(argType.type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): Injected<((A) -> T)?> = factoryOrNull(argType.type, type as Type, tag) as Injected<((A) -> T)?>

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): Injected<((A) -> T)?> = factoryOrNull(argType.type, type.type, tag) as Injected<((A) -> T)?>



        @JvmOverloads
        fun provider(type: Type, tag: Any? = null): Injected<() -> Any> = _register(InjectedProvider(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> provider(type: Class<T>, tag: Any? = null): Injected<() -> T> = provider(type as Type, tag) as Injected<() -> T>

        @JvmOverloads
        fun <T : Any> provider(type: TypeToken<T>, tag: Any? = null): Injected<() -> T> = provider(type.type, tag) as Injected<() -> T>



        @JvmOverloads
        fun providerOrNull(type: Type, tag: Any? = null): Injected<(() -> Any)?> = _register(InjectedNullableProvider(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> providerOrNull(type: Class<T>, tag: Any? = null): Injected<(() -> T)?> = providerOrNull(type as Type, tag) as Injected<(() -> T)?>

        @JvmOverloads
        fun <T : Any> providerOrNull(type: TypeToken<T>, tag: Any? = null): Injected<(() -> T)?> = providerOrNull(type.type, tag) as Injected<(() -> T)?>



        @JvmOverloads
        fun instance(type: Type, tag: Any? = null): Injected<Any> = _register(InjectedInstance(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> instance(type: Class<T>, tag: Any? = null): Injected<T> = instance(type as Type, tag) as Injected<T>

        @JvmOverloads
        fun <T : Any> instance(type: TypeToken<T>, tag: Any? = null): Injected<T> = instance(type.type, tag) as Injected<T>



        fun instanceOrNull(type: Type, tag: Any? = null): Injected<Any?> = _register(InjectedNullableInstance(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> instanceOrNull(type: Class<T>, tag: Any? = null): Injected<T?> = instanceOrNull(type as Type, tag) as Injected<T?>

        @JvmOverloads
        fun <T : Any> instanceOrNull(type: TypeToken<T>, tag: Any? = null): Injected<T?> = instanceOrNull(type.type, tag) as Injected<T?>

    }

    val typed = TInjector()


    inline fun <reified A, reified T : Any> factory(tag: Any? = null): Injected<(A) -> T> = typed.factory(typeToken<A>(), typeToken<T>(), tag)
    inline fun <reified A, reified T : Any> factoryOrNull(tag: Any? = null): Injected<((A) -> T)?> = typed.factoryOrNull(typeToken<A>(), typeToken<T>(), tag)

    inline fun <reified T : Any> provider(tag: Any? = null): Injected<() -> T> = typed.provider(typeToken<T>(), tag)
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): Injected<(() -> T)?> = typed.providerOrNull(typeToken<T>(), tag)

    inline fun <reified T : Any> instance(tag: Any? = null): Injected<T> = typed.instance(typeToken<T>(), tag)
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Injected<T?> = typed.instanceOrNull(typeToken<T>(), tag)

    inner class CurriedFactory<A>(val arg: A, val argType: TypeToken<A>) {
        // https://youtrack.jetbrains.com/issue/KT-12126
        fun <T> _register(injected: Injected<T>) = this@KodeinInjector._register(injected)

        inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T> = typed.factory(argType, typeToken<T>(), tag).toProvider(arg)
        inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<(() -> T)?> = typed.factoryOrNull(argType, typeToken<T>(), tag).toNullableProvider(arg)

        inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T> = typed.factory(argType, typeToken<T>(), tag).toInstance(arg)
        inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?> = typed.factoryOrNull(argType, typeToken<T>(), tag).toNullableInstance(arg)
    }

    inline fun <reified A> with(arg: A) = CurriedFactory(arg, typeToken<A>())

    inline fun <reified A, reified T : Any> providerFromFactory(arg: A, tag: Any? = null): Lazy<() -> T> = factory<A, T>(tag).toProvider(arg)
    inline fun <reified A, reified T : Any> providerFromFactoryOrNull(arg: A, tag: Any? = null): Lazy<(() -> T)?> = factoryOrNull<A, T>(tag).toNullableProvider(arg)

    inline fun <reified A, reified T : Any> instanceFromFactory(arg: A, tag: Any? = null): Lazy<T> = factory<A, T>(tag).toInstance(arg)
    inline fun <reified A, reified T : Any> instanceFromFactoryOrNull(arg: A, tag: Any? = null): Lazy<T?> = factoryOrNull<A, T>(tag).toNullableInstance(arg)


    fun kodein(): Lazy<Kodein> = lazy { _kodein ?: throw KodeinInjector.UninjectedException() }

    fun inject(kodein: Kodein) {
        if (_kodein != null)
            return

        synchronized(this@KodeinInjector) {
            if (_kodein != null)
                return

            _kodein = kodein
        }

        _list.forEach { it._inject(kodein._container) }
        _list.clear()

        _onInjecteds.forEach { it(kodein) }
        _onInjecteds.clear()
    }
}

fun <A, T : Any> Injected<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }
fun <A, T : Any> Injected<((A) -> T)?>.toNullableProvider(arg: A): Lazy<(() -> T)?> = lazy {
    val v = value ?: return@lazy null
    return@lazy { v(arg) }
}
fun <A, T : Any> Injected<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { value(arg) }
fun <A, T : Any> Injected<((A) -> T)?>.toNullableInstance(arg: A): Lazy<T?> = lazy {
    val v = value ?: return@lazy null
    return@lazy v(arg)
}
