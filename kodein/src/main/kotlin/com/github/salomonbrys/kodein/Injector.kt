package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinContainer
import java.io.Serializable
import java.util.*
import kotlin.reflect.KProperty

private object UNINITIALIZED_VALUE

abstract class Injected<out T : Any> internal constructor(val bind: Kodein.Bind) : Serializable {
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

//public fun <T : Any> Injected<T>.get(thisRef: Any?, property: PropertyMetadata): T = value

class InjectedFactory<in A, out T : Any>(private val _key: Kodein.Key) : Injected<(A) -> T>(_key.bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullFactory<A, T>(_key)
    override val _type = "factory"
}

class InjectedProvider<out T : Any>(bind: Kodein.Bind) : Injected<() -> T>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider<T>(bind);
    override val _type = "provider"
}

class InjectedInstance<out T : Any>(bind: Kodein.Bind) : Injected<T>(bind) {
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider<T>(bind).invoke();
    override val _type = "instance"
}

class KodeinInjector() {

    class UninjectedException : RuntimeException("Value has not been injected")

    private val _list = LinkedList<Injected<*>>()

    private var _kodein: Kodein? = null

    private var _onInjected: (Kodein) -> Unit = {}

    fun onInjected(cb: (Kodein) -> Unit) { _onInjected = cb }

    fun <T : Any> _register(injected: Injected<T>) = injected.apply { _list.add(this) }

    inline fun <reified A, reified T : Any> factory(tag: Any? = null): Injected<(A) -> T> = _register(InjectedFactory(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>())))

    inline fun <reified T : Any> provider(tag: Any? = null): Injected<() -> T> = _register(InjectedProvider(Kodein.Bind(typeToken<T>(), tag)))

    inline fun <reified T : Any> instance(tag: Any? = null): Injected<T> = _register(InjectedInstance(Kodein.Bind(typeToken<T>(), tag)))

    fun kodein(): Lazy<Kodein> = lazy { _kodein ?: throw KodeinInjector.UninjectedException() }

    fun inject(kodein: Kodein) {
        _list.forEach { it._inject(kodein._container) }
        _kodein = kodein
        _onInjected(kodein)
    }
}

fun <A, T : Any> Injected<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { this.value(arg) } }
fun <A, T : Any> Injected<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { this.value(arg) }
