package org.kodein

import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Object to indicate that the value of an [InjectedProperty] has not been initialized.
 */
private object UNINITIALIZED_VALUE

/**
 * Read only property delegate for an injected value.
 *
 * @property key The key of the value that will be injected.
 */
abstract class InjectedProperty<out T> internal constructor(val key: Kodein.Key<*, *>, val receiver: Any?) : Serializable, ReadOnlyProperty<Any?, T> {

    /**
     * Value that will be initialized when the [KodeinInjector] that created this property will be [injected][KodeinInjector.inject].
     */
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE

    private val _lock = Any()

    /**
     * The injected value.
     *
     * @throws KodeinInjector.UninjectedException If the value is accessed before the [KodeinInjector] that created this property is [injected][KodeinInjector.inject].
     */
    val value: T
        @Suppress("UNCHECKED_CAST")
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE)
                return _v1 as T

            return synchronized(_lock) {
                val _v2 = _value
                if (_v2 !== UNINITIALIZED_VALUE)
                    _v2 as T
                else
                    throw KodeinInjector.UninjectedException()
            }
        }

    /**
     * Get the injected value.
     *
     * @throws KodeinInjector.UninjectedException If the value is accessed before the [KodeinInjector] that created this property is [injected][KodeinInjector.inject].
     */
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    /**
     * @return Whether or not the [KodeinInjector] that created this property has been [injected][KodeinInjector.inject]
     *         and if it is therefore safe to access the value.
     */
    fun isInjected(): Boolean = _value !== UNINITIALIZED_VALUE

    /**
     * Stringify the injected value *if it has been injected*.
     */
    override fun toString(): String = if (isInjected()) value.toString() else "Uninjected $_type: $key."

    /**
     * Injects this property: initializes it.
     *
     * @param container The container from which to retrieve the value.
     * @throws Kodein.NotFoundException if requesting a value that is not bound.
     * @throws Kodein.DependencyLoopException if requesting a value whose construction triggered a dependency loop.
     */
    internal fun _inject(container: KodeinContainer, receiver: Any?) {
        _value = _getInjection(container, receiver)
    }

    /**
     * Gets the injected value from the container.
     */
    protected abstract fun _getInjection(container: KodeinContainer, receiver: Any?): T

    /**
     * The type of the object to inject, *used for debug print only*.
     */
    protected abstract val _type: String
}

/**
 * A read-only property delegate that injects a factory.
 *
 * @param key The key of the factory that will be injected.
 */
internal class InjectedFactoryProperty<in A, out T : Any>(key: Kodein.Key<A, T>, receiver: Any?) : InjectedProperty<(A) -> T>(key, receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.nonNullFactory(key, receiver) as (A) -> T
    override val _type = "factory"
}

/**
 * A read-only property delegate that injects a factory, or null if none is found.
 *
 * @param key The key of the factory that will be injected.
 */
internal class InjectedNullableFactoryProperty<in A, out T : Any>(key: Kodein.Key<A, T>, receiver: Any?) : InjectedProperty<((A) -> T)?>(key, receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.factoryOrNull(key, receiver) as ((A) -> T)?
    override val _type = "factory"
}

/**
 * A read-only property delegate that injects a provider.
 *
 * @param bind The bind (type & tag) of the provider that will be injected.
 */
internal class InjectedProviderProperty<out T : Any>(bind: Kodein.Bind<T>, receiver: Any?) : InjectedProperty<() -> T>(Kodein.Key(bind, UnitToken), receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.nonNullProvider(key.bind, receiver) as () -> T
    override val _type = "provider"
}

/**
 * A read-only property delegate that injects a provider, or null if none is found.
 *
 * @param bind The bind (type & tag) of the provider that will be injected.
 */
internal class InjectedNullableProviderProperty<out T : Any>(bind: Kodein.Bind<T>, receiver: Any?) : InjectedProperty<(() -> T)?>(Kodein.Key(bind, UnitToken), receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.providerOrNull(key.bind, receiver) as (() -> T)?
    override val _type = "provider"
}

/**
 * A read-only property delegate that injects an instance.
 *
 * @param bind The bind (type & tag) of the provider that will be used to get the instance.
 */
internal class InjectedInstanceProperty<out T : Any>(bind: Kodein.Bind<T>, receiver: Any?) : InjectedProperty<T>(Kodein.Key(bind, UnitToken), receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.nonNullProvider(key.bind, receiver).invoke() as T
    override val _type = "instance"
}

/**
 * A read-only property delegate that injects an instance, or null if none is found.
 *
 * @param bind The bind (type & tag) of the provider that will be used to get the instance.
 */
internal class InjectedNullableInstanceProperty<out T : Any>(bind: Kodein.Bind<T>, receiver: Any?) : InjectedProperty<T?>(Kodein.Key(bind, UnitToken), receiver) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer, receiver: Any?) = container.providerOrNull(key.bind, receiver)?.invoke() as T?
    override val _type = "instance"
}
