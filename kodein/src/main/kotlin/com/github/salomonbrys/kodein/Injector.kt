package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinContainer
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.KProperty

private object UNINITIALIZED_VALUE

abstract class Injected<out T> internal constructor(val bind: Kodein.Bind) : Serializable {
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE

    val value: T
        @Suppress("UNCHECKED_CAST")
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE)
                return _v1 as T

            return synchronized(this) {
                val _v2 = _value
                if (_v2 !== UNINITIALIZED_VALUE)
                    _v2 as T
                else
                    throw KodeinInjector.UninjectedException()
            }
        }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    fun isInjected(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInjected()) value.toString() else "Uninjected $_type: $bind."

    internal fun _inject(container: KodeinContainer) {
        _value = _getInjection(container)
    }

    protected abstract fun _getInjection(container: KodeinContainer): T
    protected abstract val _type: String
}

class InjectedFactory<in A, out T : Any>(private val _key: Kodein.Key) : Injected<(A) -> T>(_key.bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullFactory<A, T>(_key)
    override val _type = "factory"
}

class InjectedNullableFactory<in A, out T : Any>(private val _key: Kodein.Key) : Injected<((A) -> T)?>(_key.bind) {
    override fun _getInjection(container: KodeinContainer) = container.factoryOrNull<A, T>(_key)
    override val _type = "factory"
}

class InjectedProvider<out T : Any>(bind: Kodein.Bind) : Injected<() -> T>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider<T>(bind)
    override val _type = "provider"
}

class InjectedNullableProvider<out T : Any>(bind: Kodein.Bind) : Injected<(() -> T)?>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull<T>(bind)
    override val _type = "provider"
}

class InjectedInstance<out T : Any>(bind: Kodein.Bind) : Injected<T>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider<T>(bind).invoke()
    override val _type = "instance"
}

class InjectedNullableInstance<out T : Any>(bind: Kodein.Bind) : Injected<T?>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull<T>(bind)?.invoke()
    override val _type = "instance"
}


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

    fun <T> _register(injected: Injected<T>): Injected<T> {
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

    inline fun <reified A, reified T : Any> factory(tag: Any? = null): Injected<(A) -> T> = _register(InjectedFactory(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>())))
    inline fun <reified A, reified T : Any> factoryOrNull(tag: Any? = null): Injected<((A) -> T)?> = _register(InjectedNullableFactory(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>())))

    inline fun <reified T : Any> provider(tag: Any? = null): Injected<() -> T> = _register(InjectedProvider(Kodein.Bind(typeToken<T>(), tag)))
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): Injected<(() -> T)?> = _register(InjectedNullableProvider(Kodein.Bind(typeToken<T>(), tag)))

    inline fun <reified T : Any> instance(tag: Any? = null): Injected<T> = _register(InjectedInstance(Kodein.Bind(typeToken<T>(), tag)))
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Injected<T?> = _register(InjectedNullableInstance(Kodein.Bind(typeToken<T>(), tag)))

    inner class CurriedFactory<A>(val arg: A, val argType: Type) {
        // https://youtrack.jetbrains.com/issue/KT-12126
        fun <T> _register(injected: Injected<T>) = this@KodeinInjector._register(injected)

        inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T> = _register(InjectedFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), argType))).toProvider(arg)
        inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<(() -> T)?> = _register(InjectedNullableFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), argType))).toNullableProvider(arg)

        inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T> = _register(InjectedFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), argType))).toInstance(arg)
        inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?> = _register(InjectedNullableFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), argType))).toNullableInstance(arg)
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
