package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinContainer
import java.io.Serializable
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
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullFactory(_key) as (A) -> T
    override val _type = "factory"
}

class InjectedNullableFactory<in A, out T : Any>(private val _key: Kodein.Key) : Injected<((A) -> T)?>(_key.bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.factoryOrNull(_key) as ((A) -> T)?
    override val _type = "factory"
}

class InjectedProvider<out T : Any>(bind: Kodein.Bind) : Injected<() -> T>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(bind) as () -> T
    override val _type = "provider"
}

class InjectedNullableProvider<out T : Any>(bind: Kodein.Bind) : Injected<(() -> T)?>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(bind) as (() -> T)?
    override val _type = "provider"
}

class InjectedInstance<out T : Any>(bind: Kodein.Bind) : Injected<T>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(bind).invoke() as T
    override val _type = "instance"
}

class InjectedNullableInstance<out T : Any>(bind: Kodein.Bind) : Injected<T?>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(bind)?.invoke() as T?
    override val _type = "instance"
}
