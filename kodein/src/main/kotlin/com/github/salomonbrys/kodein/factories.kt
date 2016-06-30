@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Binds a type to a factory.
 */
class CFactory<in A, out T : Any>(argType: Type, createdType: Type, val factory: Kodein.(A) -> T) : AFactory<A, T>("factory", argType, createdType) {
    override fun getInstance(kodein: Kodein, key: Kodein.Key, arg: A) = this.factory(kodein, arg)

}

inline fun <reified A, reified T : Any> Kodein.Builder.factory(noinline factory: Kodein.(A) -> T): CFactory<A, T>
        = CFactory(typeToken<A>().type, typeToken<T>().type, factory)



/**
 * Binds a type to a provider.
 */
class CProvider<out T : Any>(createdType: Type, val provider: Kodein.() -> T) : AProvider<T>("provider", createdType) {
    override fun getInstance(kodein: Kodein) = this.provider(kodein)
}

inline fun <reified T : Any> Kodein.Builder.provider(noinline provider: Kodein.() -> T) = CProvider(typeToken<T>().type, provider)



/**
 * Base class for CSingleton & CEagerSingleton.
 */
abstract class ASingleton<out T : Any>(factoryName: String, createdType: Type, val creator: Kodein.() -> T) : AProvider<T>(factoryName, createdType) {

    private var _instance: T? = null
    private val _lock = Any()

    override fun getInstance(kodein: Kodein): T {
        if (_instance != null)
            return _instance!!
        else
            synchronized(_lock) {
                if (_instance == null)
                    _instance = kodein.creator()
                return _instance!!
            }
    }
}



/**
 * Binds a lazily instanciated singleton.
 */
class CSingleton<out T : Any>(createdType: Type, creator: Kodein.() -> T) : ASingleton<T>("singleton", createdType, creator)

inline fun <reified T : Any> Kodein.Builder.singleton(noinline creator: Kodein.() -> T): AProvider<T> = CSingleton(typeToken<T>().type, creator)



/**
 * Binds an eagerly instanciated singleton.
 */
class CEagerSingleton<out T : Any>(builder: Kodein.Builder, createdType: Type, creator: Kodein.() -> T) : ASingleton<T>("eagerSingleton", createdType, creator) {
    init {
        builder.onReady { getInstance(this) }
    }
}

inline fun <reified T : Any> Kodein.Builder.eagerSingleton(noinline creator: Kodein.() -> T): AProvider<T> = CEagerSingleton(this, typeToken<T>().type, creator)



/**
 * Binds a type to a lazily instanciated thread local singleton.
 */
class CThreadSingleton<T : Any>(createdType: Type, val creator: Kodein.() -> T) : AProvider<T>("threadSingleton", createdType) {

    private val _storage = ThreadLocal<T>()

    override fun getInstance(kodein: Kodein): T {
        var instance = _storage.get()
        if (instance == null) {
            instance = kodein.creator()
            _storage.set(instance)
        }
        return instance
    }
}

inline fun <reified T : Any> Kodein.Builder.threadSingleton(noinline creator: Kodein.() -> T): AProvider<T> = CThreadSingleton(typeToken<T>().type, creator)



/**
 * Binds a type to an instance.
 */
class CInstance<out T : Any>(createdType: Type, val instance: T) : AProvider<T>("instance", createdType) {
    override fun getInstance(kodein: Kodein): T = this.instance

    override val description: String get() = "$factoryName ( ${createdType.dispName} ) "
}

inline fun <reified T : Any> Kodein.Builder.instance(instance: T) = CInstance(typeToken<T>().type, instance)
