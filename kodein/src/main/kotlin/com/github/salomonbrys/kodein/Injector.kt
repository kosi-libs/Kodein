package com.github.salomonbrys.kodein

import java.io.Serializable
import java.util.*
import kotlin.reflect.KProperty

private object UNINITIALIZED_VALUE

public abstract class Injected<out T : Any> internal constructor(public val bind: Kodein.Bind) : Serializable {
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE

    public val value: T
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

    public operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    public fun isInjected(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInjected()) value.toString() else "Uninjected $_type: $bind."

    internal fun _inject(container: Container) {
        _value = _getInjection(container)
    }

    protected abstract fun _getInjection(container: Container): T
    protected abstract val _type: String
}

//public fun <T : Any> Injected<T>.get(thisRef: Any?, property: PropertyMetadata): T = value

public class InjectedFactory<in A, out T : Any>(private val _key: Kodein.Key) : Injected<(A) -> T>(_key.bind) {
    override fun _getInjection(container: Container) = container.nonNullFactory<A, T>(_key)
    override val _type = "factory"
}

public class InjectedProvider<out T : Any>(bind: Kodein.Bind) : Injected<() -> T>(bind) {
    override fun _getInjection(container: Container) = container.nonNullProvider<T>(bind);
    override val _type = "provider"
}

public class InjectedInstance<out T : Any>(bind: Kodein.Bind) : Injected<T>(bind) {
    override fun _getInjection(container: Container) = container.nonNullProvider<T>(bind).invoke();
    override val _type = "instance"
}

public class KodeinInjector() {

    public class UninjectedException : RuntimeException("Value has not been injected")

    private val _list = LinkedList<Injected<*>>()

    private var _kodein: Kodein? = null

    private var _onInjected: (Kodein) -> Unit = {}

    public fun onInjected(cb: (Kodein) -> Unit) { _onInjected = cb }

    public fun <T : Any> _register(injected: Injected<T>) = injected.apply { _list.add(this) }

    public inline fun <reified A, reified T : Any> factory(tag: Any? = null): Injected<(A) -> T> = _register(InjectedFactory(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>())))

    public inline fun <reified T : Any> provider(tag: Any? = null): Injected<() -> T> = _register(InjectedProvider(Kodein.Bind(typeToken<T>(), tag)))

    public inline fun <reified T : Any> instance(tag: Any? = null): Injected<T> = _register(InjectedInstance(Kodein.Bind(typeToken<T>(), tag)))

    public fun kodein(): Lazy<Kodein> = lazy { _kodein ?: throw KodeinInjector.UninjectedException() }

    public fun inject(kodein: Kodein) {
        _list.forEach { it._inject(kodein._container) }
        _kodein = kodein
        _onInjected(kodein)
    }
}

public fun <A, T : Any> Injected<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { this.value(arg) } }
public fun <A, T : Any> Injected<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { this.value(arg) }
