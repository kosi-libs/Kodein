package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.KClass

@Suppress("unused")
class KodeinInjector() : KodeinInjectedBase {

    override val injector = this

    class UninjectedException : RuntimeException("Value has not been injected")

    private val _list = LinkedList<InjectedProperty<*>>()

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

    private fun <T> _register(injected: InjectedProperty<T>): InjectedProperty<T> {
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
        fun factory(argType: Type, type: Type, tag: Any? = null): InjectedProperty<(Any?) -> Any> = _register(InjectedFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: Class<T>, tag: Any? = null): InjectedProperty<(Any?) -> T> = factory(argType, type as Type, tag) as InjectedProperty<(Any?) -> T>

        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(Any?) -> T> = factory(argType, type.type, tag) as InjectedProperty<(Any?) -> T>

        @JvmOverloads
        fun <A> factory(argType: Class<A>, type: Type, tag: Any? = null): InjectedProperty<(A) -> Any> = factory(argType as Type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: Class<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType as Type, type as Type, tag) as InjectedProperty<(A) -> T>

        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType as Type, type.type, tag) as InjectedProperty<(A) -> T>

        @JvmOverloads
        fun <A> factory(argType: TypeToken<A>, type: Type, tag: Any? = null): InjectedProperty<(A) -> Any> = factory(argType.type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType.type, type as Type, tag) as InjectedProperty<(A) -> T>

        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType.type, type.type, tag) as InjectedProperty<(A) -> T>



        @JvmOverloads
        fun factoryOrNull(argType: Type, type: Type, tag: Any? = null): InjectedProperty<((Any?) -> Any)?> = _register(InjectedNullableFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: Class<T>, tag: Any? = null): InjectedProperty<((Any?) -> T)?> = factoryOrNull(argType, type as Type, tag) as InjectedProperty<((Any?) -> T)?>

        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((Any?) -> T)?> = factoryOrNull(argType, type.type, tag) as InjectedProperty<((Any?) -> T)?>

        @JvmOverloads
        fun <A> factoryOrNull(argType: Class<A>, type: Type, tag: Any? = null): InjectedProperty<((A) -> Any)?> = factoryOrNull(argType as Type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: Class<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType as Type, type as Type, tag) as InjectedProperty<((A) -> T)?>

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType as Type, type.type, tag) as InjectedProperty<((A) -> T)?>

        @JvmOverloads
        fun <A> factoryOrNull(argType: TypeToken<A>, type: Type, tag: Any? = null): InjectedProperty<((A) -> Any)?> = factoryOrNull(argType.type, type, tag)

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType.type, type as Type, tag) as InjectedProperty<((A) -> T)?>

        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType.type, type.type, tag) as InjectedProperty<((A) -> T)?>



        @JvmOverloads
        fun provider(type: Type, tag: Any? = null): InjectedProperty<() -> Any> = _register(InjectedProviderProperty(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> provider(type: Class<T>, tag: Any? = null): InjectedProperty<() -> T> = provider(type as Type, tag) as InjectedProperty<() -> T>

        @JvmOverloads
        fun <T : Any> provider(type: TypeToken<T>, tag: Any? = null): InjectedProperty<() -> T> = provider(type.type, tag) as InjectedProperty<() -> T>



        @JvmOverloads
        fun providerOrNull(type: Type, tag: Any? = null): InjectedProperty<(() -> Any)?> = _register(InjectedNullableProviderProperty(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> providerOrNull(type: Class<T>, tag: Any? = null): InjectedProperty<(() -> T)?> = providerOrNull(type as Type, tag) as InjectedProperty<(() -> T)?>

        @JvmOverloads
        fun <T : Any> providerOrNull(type: TypeToken<T>, tag: Any? = null): InjectedProperty<(() -> T)?> = providerOrNull(type.type, tag) as InjectedProperty<(() -> T)?>



        @JvmOverloads
        fun instance(type: Type, tag: Any? = null): InjectedProperty<Any> = _register(InjectedInstanceProperty(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> instance(type: Class<T>, tag: Any? = null): InjectedProperty<T> = instance(type as Type, tag) as InjectedProperty<T>

        @JvmOverloads
        fun <T : Any> instance(type: TypeToken<T>, tag: Any? = null): InjectedProperty<T> = instance(type.type, tag) as InjectedProperty<T>



        fun instanceOrNull(type: Type, tag: Any? = null): InjectedProperty<Any?> = _register(InjectedNullableInstanceProperty(Kodein.Bind(type, tag)))

        @JvmOverloads
        fun <T : Any> instanceOrNull(type: Class<T>, tag: Any? = null): InjectedProperty<T?> = instanceOrNull(type as Type, tag) as InjectedProperty<T?>

        @JvmOverloads
        fun <T : Any> instanceOrNull(type: TypeToken<T>, tag: Any? = null): InjectedProperty<T?> = instanceOrNull(type.type, tag) as InjectedProperty<T?>

    }

    val typed = TInjector()

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

inline fun <reified T : Any, reified R : Any> T.instanceForClass(injector: KodeinInjector, tag: Any? = null): Lazy<R> = injector.with(T::class as KClass<*>).instance<R>(tag)

inline fun <reified T : Any, reified R : Any> T.providerForClass(injector: KodeinInjector, tag: Any? = null): Lazy<() -> R> = injector.with(T::class as KClass<*>).provider<R>(tag)
