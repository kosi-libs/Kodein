package com.github.salomonbrys.kodein

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
abstract class InjectedProperty<out T> internal constructor(val key: Kodein.Key<*, *>) : Serializable, ReadOnlyProperty<Any?, T> {

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
    internal fun _inject(container: KodeinContainer) {
        _value = _getInjection(container)
    }

    /**
     * Gets the injected value from the container.
     */
    protected abstract fun _getInjection(container: KodeinContainer): T

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
class InjectedFactoryProperty<in A, out T : Any>(key: Kodein.Key<A, T>) : InjectedProperty<(A) -> T>(key) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullFactory(key) as (A) -> T
    override val _type = "factory"
}

/**
 * A read-only property delegate that injects a factory, or null if none is found.
 *
 * @param key The key of the factory that will be injected.
 */
class InjectedNullableFactoryProperty<in A, out T : Any>(key: Kodein.Key<A, T>) : InjectedProperty<((A) -> T)?>(key) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.factoryOrNull(key) as ((A) -> T)?
    override val _type = "factory"
}

/**
 * A read-only property delegate that injects a provider.
 *
 * @param bind The bind (type & tag) of the provider that will be injected.
 */
class InjectedProviderProperty<out T : Any>(bind: Kodein.Bind<T>) : InjectedProperty<() -> T>(Kodein.Key(bind, UnitToken)) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(key.bind) as () -> T
    override val _type = "provider"
}

/**
 * A read-only property delegate that injects a provider, or null if none is found.
 *
 * @param bind The bind (type & tag) of the provider that will be injected.
 */
class InjectedNullableProviderProperty<out T : Any>(bind: Kodein.Bind<T>) : InjectedProperty<(() -> T)?>(Kodein.Key(bind, UnitToken)) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(key.bind) as (() -> T)?
    override val _type = "provider"
}

/**
 * A read-only property delegate that injects an instance.
 *
 * @param bind The bind (type & tag) of the provider that will be used to get the instance.
 */
class InjectedInstanceProperty<out T : Any>(bind: Kodein.Bind<T>) : InjectedProperty<T>(Kodein.Key(bind, UnitToken)) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.nonNullProvider(key.bind).invoke() as T
    override val _type = "instance"
}

/**
 * A read-only property delegate that injects an instance, or null if none is found.
 *
 * @param bind The bind (type & tag) of the provider that will be used to get the instance.
 */
class InjectedNullableInstanceProperty<out T : Any>(bind: Kodein.Bind<T>) : InjectedProperty<T?>(Kodein.Key(bind, UnitToken)) {
    @Suppress("UNCHECKED_CAST")
    override fun _getInjection(container: KodeinContainer) = container.providerOrNull(key.bind)?.invoke() as T?
    override val _type = "instance"
}



/**
 * Transforms an injected factory property into an injected provider property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected provider property that, when called, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> InjectedProperty<(A) -> T>.toProvider(crossinline arg: () -> A): Lazy<() -> T> = lazy { { value(arg()) } }

/**
 * Transforms an injected nullable factory property into an injected nullable provider property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected provider property that, when called, will call the receiver factory (if not null) with the given argument.
 */
inline fun <A, T : Any> InjectedProperty<((A) -> T)?>.toProviderOrNull(crossinline arg: () -> A): Lazy<(() -> T)?> = lazy {
    val v = value ?: return@lazy null
    return@lazy { v(arg()) }
}

/**
 * Transforms an injected factory property into an injected instance property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected instance property that, when injected, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> InjectedProperty<(A) -> T>.toInstance(crossinline arg: () -> A): Lazy<T> = lazy { value(arg()) }

/**
 * Transforms an injected factory property into an injected instance property by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The injected factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An injected instance property that, when injected, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> InjectedProperty<((A) -> T)?>.toInstanceOrNull(crossinline arg: () -> A): Lazy<T?> = lazy {
    val v = value ?: return@lazy null
    return@lazy v(arg())
}
