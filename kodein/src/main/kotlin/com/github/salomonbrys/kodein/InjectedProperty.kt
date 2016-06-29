package com.github.salomonbrys.kodein

import java.io.Serializable
import kotlin.reflect.KProperty

private object UNINITIALIZED_VALUE

abstract class InjectedProperty<out T> internal constructor(val bind: Kodein.Bind) : Serializable {
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

class InjectedFactoryProperty<in A, out T : Any>(private val _key: Kodein.Key) : InjectedProperty<(A) -> T>(_key.bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullFactory(_key) as (A) -> T
    override val _type = "factory"
}

class InjectedNullableFactoryProperty<in A, out T : Any>(private val _key: Kodein.Key) : InjectedProperty<((A) -> T)?>(_key.bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.factoryOrNull(_key) as ((A) -> T)?
    override val _type = "factory"
}

class InjectedProviderProperty<out T : Any>(bind: Kodein.Bind) : InjectedProperty<() -> T>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(bind) as () -> T
    override val _type = "provider"
}

class InjectedNullableProviderProperty<out T : Any>(bind: Kodein.Bind) : InjectedProperty<(() -> T)?>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(bind) as (() -> T)?
    override val _type = "provider"
}

class InjectedInstanceProperty<out T : Any>(bind: Kodein.Bind) : InjectedProperty<T>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(bind).invoke() as T
    override val _type = "instance"
}

class InjectedNullableInstanceProperty<out T : Any>(bind: Kodein.Bind) : InjectedProperty<T?>(bind) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(bind)?.invoke() as T?
    override val _type = "instance"
}



fun <A, T : Any> InjectedProperty<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }

fun <A, T : Any> InjectedProperty<((A) -> T)?>.toNullableProvider(arg: A): Lazy<(() -> T)?> = lazy {
    val v = value ?: return@lazy null
    return@lazy { v(arg) }
}

fun <A, T : Any> InjectedProperty<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { value(arg) }

fun <A, T : Any> InjectedProperty<((A) -> T)?>.toNullableInstance(arg: A): Lazy<T?> = lazy {
    val v = value ?: return@lazy null
    return@lazy v(arg)
}
