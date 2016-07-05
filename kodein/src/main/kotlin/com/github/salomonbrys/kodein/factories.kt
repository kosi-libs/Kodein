@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type object of the argument used by this factory, *used for debug print only*.
 * @param createdType The type of objects created by this factory, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class CFactory<in A, out T : Any>(argType: Type, createdType: Type, val creator: Kodein.(A) -> T) : AFactory<A, T>("factory", argType, createdType) {
    override fun getInstance(kodein: Kodein, key: Kodein.Key, arg: A) = this.creator(kodein, arg)

}

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.factory(noinline creator: Kodein.(A) -> T): CFactory<A, T>
        = CFactory(typeToken<A>().type, typeToken<T>().type, creator)



/**
 * Concrete provider: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [CFactory], but without argument.
 *
 * @param T The created type.
 * @param createdType The type of objects created by this provider, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class CProvider<out T : Any>(createdType: Type, val creator: Kodein.() -> T) : AProvider<T>("provider", createdType) {
    override fun getInstance(kodein: Kodein, key: Kodein.Key) = this.creator(kodein)
}

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.provider(noinline creator: Kodein.() -> T) = CProvider(typeToken<T>().type, creator)



/**
 * Singleton base: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param factoryName The name of this singleton factory, *used for debug print only*.
 * @param createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
abstract class ASingleton<out T : Any>(factoryName: String, createdType: Type, val creator: Kodein.() -> T) : AProvider<T>(factoryName, createdType) {

    private var _instance: T? = null
    private val _lock = Any()

    override fun getInstance(kodein: Kodein, key: Kodein.Key): T {
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
 * Concrete singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
class CSingleton<out T : Any>(createdType: Type, creator: Kodein.() -> T) : ASingleton<T>("singleton", createdType, creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.singleton(noinline creator: Kodein.() -> T): AProvider<T> = CSingleton(typeToken<T>().type, creator)



/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
class CEagerSingleton<out T : Any>(builder: Kodein.Builder, createdType: Type, creator: Kodein.() -> T) : ASingleton<T>("eagerSingleton", createdType, creator) {
    init {
        builder.onReady { getInstance(this, Kodein.Key(Kodein.Bind(createdType, null), Unit::class.java)) }
    }
}

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.eagerSingleton(noinline creator: Kodein.() -> T): AProvider<T> = CEagerSingleton(this, typeToken<T>().type, creator)



/**
 * Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * @param T The created type.
 * @param createdType The type of the created objects, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 */
class CThreadSingleton<out T : Any>(createdType: Type, val creator: Kodein.() -> T) : AProvider<T>("threadSingleton", createdType) {

    private val _storage = ThreadLocal<T>()

    override fun getInstance(kodein: Kodein, key: Kodein.Key): T {
        var instance = _storage.get()
        if (instance == null) {
            instance = kodein.creator()
            _storage.set(instance)
        }
        return instance
    }
}

/**
 * Creates a thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 * @return A thread singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.threadSingleton(noinline creator: Kodein.() -> T): AProvider<T> = CThreadSingleton(typeToken<T>().type, creator)



/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param instanceType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class CInstance<out T : Any>(instanceType: Type, val instance: T) : AProvider<T>("instance", instanceType) {
    override fun getInstance(kodein: Kodein, key: Kodein.Key): T = this.instance

    override val description: String get() = "$factoryName ( ${createdType.simpleDispString} ) "
    override val fullDescription: String get() = "$factoryName ( ${createdType.fullDispString} ) "
}

/**
 * Creates an instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.instance(instance: T) = CInstance(typeToken<T>().type, instance)
