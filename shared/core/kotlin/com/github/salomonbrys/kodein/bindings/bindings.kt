package com.github.salomonbrys.kodein.bindings

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.internal.synchronizedIfNull

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class FactoryBinding<in A, T: Any>(override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, val creator: BindingKodein.(A) -> T) : Binding<A, T> {
    override fun factoryName() = "factory"

    override fun getInstance(kodein: BindingKodein, key: Kodein.Key<A, T>, arg: A) = this.creator(kodein, arg)

}

/**
 * Concrete multiton: will create one and only one instance for each argument.
 * Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.
 *
 * @param T The created type.
 * @property argType The type of the argument used for each value can there be a new instance.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once per argument. Should create a new instance.
 */
class MultitonBinding<in A, T : Any>(override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, val creator: BindingKodein.(A) -> T) : Binding<A, T> {
    private val _instances = newConcurrentMap<A, T>()

    override fun factoryName() = "multiton"

    override fun getInstance(kodein: BindingKodein, key: Kodein.Key<A, T>, arg: A): T {
        synchronizedIfNull(
                lock = _instances,
                predicate = { _instances[arg] },
                ifNotNull = { return it },
                ifNull = {
                    _instances[arg] = kodein.creator(arg)
                }
        )
        return _instances[arg]!!
    }
}

/**
 * Concrete provider: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [FactoryBinding], but without argument.
 *
 * @param T The created type.
 * @param createdType The type of objects created by this provider, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class ProviderBinding<T : Any>(override val createdType: TypeToken<out T>, val creator: NoArgBindingKodein.() -> T) : NoArgBinding<T> {
    override fun factoryName() = "provider"

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, T>) = this.creator(kodein)
}

/**
 * SingletonBinding base: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
abstract class ASingleton<T : Any> internal constructor(val creator: NoArgBindingKodein.() -> T) : NoArgBinding<T> {
    private @Volatile var _instance: T? = null
    private val _lock = Any()

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, T>): T {
        synchronizedIfNull(
                lock = _lock,
                predicate = this::_instance,
                ifNotNull = { return it },
                ifNull = {
                    _instance = kodein.creator()
                }
        )
        return _instance!!
    }
}

/**
 * Concrete singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
class SingletonBinding<T : Any>(override val createdType: TypeToken<out T>, creator: NoArgBindingKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "singleton"
}

/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
class EagerSingletonBinding<T : Any>(builder: Kodein.Builder, override val createdType: TypeToken<out T>, creator: NoArgBindingKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "eagerSingleton"

    init {
        val key = Kodein.Key(Kodein.Bind(createdType, null), UnitToken)
        builder.onProviderReady(key) { getInstance(this, key) }
    }
}

/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param createdType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class InstanceBinding<T : Any>(override val createdType: TypeToken<out T>, val instance: T) : NoArgBinding<T> {
    override fun factoryName() = "instance"

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, T>): T = this.instance

    override val description: String get() = "${factoryName()} ( ${createdType.simpleDispString()} ) "
    override val fullDescription: String get() = "${factoryFullName()} ( ${createdType.fullDispString()} ) "
}
