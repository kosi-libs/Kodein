@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type object of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class CFactory<in A, out T : Any>(argType: Type, createdType: Type, val creator: FactoryKodein.(A) -> T) : AFactory<A, T>("factory", argType, createdType) {
    override fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: A) = this.creator(kodein, arg)

}

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.genericFactory(noinline creator: FactoryKodein.(A) -> T): CFactory<A, T>
        = CFactory(genericToken<A>().type, genericToken<T>().type, creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.erasedFactory(noinline creator: FactoryKodein.(A) -> T): CFactory<A, T>
        = CFactory(typeClass<A>(), T::class.java, creator)


/**
 * Concrete provider: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [CFactory], but without argument.
 *
 * @param T The created type.
 * @param createdType The type of objects created by this provider, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class CProvider<out T : Any>(createdType: Type, val creator: ProviderKodein.() -> T) : AProvider<T>("provider", createdType) {
    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key) = this.creator(kodein)
}

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be kept.
 *
 * A provider is like a factory, but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericProvider(noinline creator: ProviderKodein.() -> T) = CProvider(genericToken<T>().type, creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a factory, but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedProvider(noinline creator: ProviderKodein.() -> T) = CProvider(T::class.java, creator)



/**
 * Singleton base: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param factoryName The name of this singleton factory, *used for debug print only*.
 * @param createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
abstract class ASingleton<out T : Any>(factoryName: String, createdType: Type, val creator: ProviderKodein.() -> T) : AProvider<T>(factoryName, createdType) {

    private var _instance: T? = null
    private val _lock = Any()

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T {
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
class CSingleton<out T : Any>(createdType: Type, creator: ProviderKodein.() -> T) : ASingleton<T>("singleton", createdType, creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CSingleton(genericToken<T>().type, creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CSingleton(T::class.java, creator)



/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
class CEagerSingleton<out T : Any>(builder: Kodein.Builder, createdType: Type, creator: ProviderKodein.() -> T) : ASingleton<T>("eagerSingleton", createdType, creator) {
    init {
        val key = Kodein.Key(Kodein.Bind(createdType, null), Unit::class.java)
        builder.onReady(key) { getInstance(ProviderKodein(this), key) }
    }
}

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericEagerSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CEagerSingleton(this, genericToken<T>().type, creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedEagerSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CEagerSingleton(this, T::class.java, creator)



/**
 * Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * @param T The created type.
 * @param createdType The type of the created objects, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 */
class CThreadSingleton<out T : Any>(createdType: Type, val creator: ProviderKodein.() -> T) : AProvider<T>("threadSingleton", createdType) {

    private val _storage = ThreadLocal<T>()

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T {
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
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 * @return A thread singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericThreadSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CThreadSingleton(genericToken<T>().type, creator)

/**
 * Creates a thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.
 * @return A thread singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedThreadSingleton(noinline creator: ProviderKodein.() -> T): AProvider<T> = CThreadSingleton(T::class.java, creator)



/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param instanceType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class CInstance<out T : Any>(instanceType: Type, val instance: T) : AProvider<T>("instance", instanceType) {
    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T = this.instance

    override val description: String get() = "$factoryName ( ${createdType.simpleDispString} ) "
    override val fullDescription: String get() = "$factoryName ( ${createdType.fullDispString} ) "
}

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be kept.
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericInstance(instance: T) = CInstance(genericToken<T>().type, instance)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedInstance(instance: T) = CInstance(T::class.java, instance)
