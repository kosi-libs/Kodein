package org.kodein.bindings

import org.kodein.Kodein
import org.kodein.TypeToken
import org.kodein.internal.synchronizedIfNull
import org.kodein.newConcurrentMap

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class FactoryBinding<A, T: Any>(override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, val creator: BindingKodein.(A) -> T) : KodeinBinding<A, T> {
    override fun factoryName() = "factory"

    override fun getFactory(kodein: BindingKodein, key: Kodein.Key<A, T>): (A) -> T = { arg -> this.creator(kodein, arg) }
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
class MultitonBinding<A, T : Any>(override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, val creator: BindingKodein.(A) -> T) : KodeinBinding<A, T> {
    private val _instances = newConcurrentMap<A, T>()
    private val _lock = Any()

    override fun factoryName() = "multiton"

    override fun getFactory(kodein: BindingKodein, key: Kodein.Key<A, T>): (A) -> T {
        return { arg ->
            synchronizedIfNull(
                    lock = _lock,
                    predicate = { _instances[arg] },
                    ifNotNull = { it },
                    ifNull = {
                        kodein.creator(arg).also { _instances[arg] = it }
                    }
            )
        }
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
class ProviderBinding<T : Any>(override val createdType: TypeToken<out T>, val creator: NoArgBindingKodein.() -> T) : NoArgKodeinBinding<T> {
    override fun factoryName() = "provider"

    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T = { this.creator(kodein) }
}

/**
 * SingletonBinding base: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
abstract class ASingleton<T : Any> internal constructor(val creator: NoArgSimpleBindingKodein.() -> T) : NoArgKodeinBinding<T> {
    private @Volatile var _instance: T? = null
    private val _lock = Any()

    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T {
        return {
            synchronizedIfNull(
                    lock = _lock,
                    predicate = this@ASingleton::_instance,
                    ifNotNull = { it },
                    ifNull = {
                        kodein.creator().also { _instance = it }
                    }
            )
        }

    }
}

/**
 * Concrete singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
class SingletonBinding<T : Any>(override val createdType: TypeToken<out T>, creator: NoArgSimpleBindingKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "singleton"
}

/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
class EagerSingletonBinding<T : Any>(builder: Kodein.Builder, override val createdType: TypeToken<out T>, creator: NoArgSimpleBindingKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "eagerSingleton"

    init {
        val bind = Kodein.Bind(createdType, null)
        builder.onProviderReady(bind) { getProvider(this, bind).invoke() }
    }
}

/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param createdType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class InstanceBinding<T : Any>(override val createdType: TypeToken<out T>, val instance: T) : NoArgKodeinBinding<T> {
    override fun factoryName() = "instance"

    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T = { this.instance }

    override val description: String get() = "${factoryName()} ( ${createdType.simpleDispString()} ) "
    override val fullDescription: String get() = "${factoryFullName()} ( ${createdType.fullDispString()} ) "
}
