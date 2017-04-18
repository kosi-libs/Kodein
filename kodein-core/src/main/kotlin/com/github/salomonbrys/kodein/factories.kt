@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
@PublishedApi
internal class CFactory<in A, out T : Any>(override val argType: Type, override val createdType: Type, val creator: FactoryKodein.(A) -> T) : Factory<A, T> {
    override fun factoryName() = "factory"

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
inline fun <reified A, reified T : Any> Kodein.Builder.genericFactory(noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
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
inline fun <reified A, reified T : Any> Kodein.Builder.erasedFactory(noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
        = CFactory(typeClass<A>(), T::class.java, creator)


/**
 * Concrete multiton: will create one and only one instance for each argument.
 * Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.
 *
 * @param T The created type.
 * @property argType The type of the argument used for each value can there be a new instance.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once per argument. Should create a new instance.
 */
@PublishedApi
internal class CMultiton<in A, out T : Any>(override val argType: Type, override val createdType: Type, val creator: FactoryKodein.(A) -> T) : Factory<A, T> {
    private val _instances = ConcurrentHashMap<A, T>()

    override fun factoryName() = "multiton"

    override fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: A): T {
        _instances[arg]?.let { return it }
        synchronized(_instances) {
            _instances[arg]?.let { return it }
            return _instances.getOrPut(arg) { kodein.creator(arg) }
        }
    }
}

/**
 * Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.genericMultiton(noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CMultiton(genericToken<A>().type, genericToken<T>().type, creator)

/**
 * Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.erasedMultiton(noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CMultiton(typeClass<A>(), T::class.java, creator)




/**
 * Concrete provider: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [CFactory], but without argument.
 *
 * @param T The created type.
 * @param createdType The type of objects created by this provider, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
@PublishedApi
internal class CProvider<out T : Any>(override val createdType: Type, val creator: ProviderKodein.() -> T) : Provider<T> {
    override fun factoryName() = "provider"

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
inline fun <reified T : Any> Kodein.Builder.genericProvider(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CProvider(genericToken<T>().type, creator)

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
inline fun <reified T : Any> Kodein.Builder.erasedProvider(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CProvider(T::class.java, creator)



/**
 * Singleton base: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
internal abstract class ASingleton<out T : Any>(val creator: ProviderKodein.() -> T) : Provider<T> {
    private @Volatile var _instance: T? = null
    private val _lock = Any()

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T {
        _instance?.let { return it }

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
@PublishedApi
internal class CSingleton<out T : Any>(override val createdType: Type, creator: ProviderKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "singleton"
}

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.genericSingleton(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CSingleton(genericToken<T>().type, creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedSingleton(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CSingleton(T::class.java, creator)



/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
@PublishedApi
internal class CEagerSingleton<out T : Any>(builder: Kodein.Builder, override val createdType: Type, creator: ProviderKodein.() -> T) : ASingleton<T>(creator) {
    override fun factoryName() = "eagerSingleton"

    init {
        val key = Kodein.Key(Kodein.Bind(createdType, null), Unit::class.java)
        builder.onProviderReady(key) { getInstance(this, key) }
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
inline fun <reified T : Any> Kodein.Builder.genericEagerSingleton(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CEagerSingleton(this, genericToken<T>().type, creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedEagerSingleton(noinline creator: ProviderKodein.() -> T): Provider<T>
    = CEagerSingleton(this, T::class.java, creator)



/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param createdType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
@PublishedApi
internal class CInstance<out T : Any>(override val createdType: Type, val instance: T) : Provider<T> {
    override fun factoryName() = "instance"

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T = this.instance

    override val description: String get() = "${factoryName()} ( ${createdType.simpleDispString} ) "
    override val fullDescription: String get() = "${factoryFullName()} ( ${createdType.fullDispString} ) "
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
inline fun <reified T : Any> Kodein.Builder.genericInstance(instance: T): Provider<T>
    = CInstance(genericToken<T>().type, instance)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
inline fun <reified T : Any> Kodein.Builder.erasedInstance(instance: T): Provider<T>
    = CInstance(T::class.java, instance)
