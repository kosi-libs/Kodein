package com.github.salomonbrys.kodein

import java.lang.reflect.Type


public interface Factory<in A, out T : Any> {
    public fun getInstance(kodein: Kodein, arg: A): T
    public val scopeName: String;
    public val argType: Type
}

public class CFactory<A, out T : Any>(override val scopeName: String, private val _provider: Kodein.(A) -> T, override val argType: Type) : Factory<A, T> {
    override fun getInstance(kodein: Kodein, arg: A) = kodein._provider(arg)
}

public inline fun <reified A, T : Any> Kodein.Builder.factory(noinline factory: Kodein.(A) -> T): Factory<A, T> {
    val type = typeToken<A>()
    return CFactory<A, T>("factory<${type.typeName}>", factory, type)
}

public class CProvider<out T : Any>(override val scopeName: String, private val _provider: Kodein.() -> T) : Factory<Unit, T> {
    override fun getInstance(kodein: Kodein, arg: Unit) = kodein._provider()
    override val argType: Type = Unit.javaClass
}

public fun <T : Any> Kodein.Builder.provider(provider: Kodein.() -> T) = CProvider("provider", provider)

/**
 * Singleton scope.
 *
 * Allows you to bind a lazily instanciated singleton with `bind<Type>() with singleton { MySingletonType() }`
 */
public fun <T : Any> Kodein.Builder.singleton(creator: Kodein.() -> T): CProvider<T> {
    var instance: T? = null
    val lock = Any()

    return CProvider("singleton") {
        if (instance != null)
            instance!!
        else
            synchronized(lock) {
                if (instance == null)
                    instance = creator()
                instance!!
            }
    }
}

/**
 * Thread singleton scope.
 *
 * Allows you to bind a lazily instanciated thread singleton with `bind<Type>() with threadSingleton { MySingletonType() }`
 */
public fun <T : Any> Kodein.Builder.threadSingleton(creator: Kodein.() -> T): CProvider<T> {
    val storage = ThreadLocal<T>()

    return CProvider("threadSingleton") {
        var instance = storage.get()
        if (instance == null) {
            instance = creator()
            storage.set(instance)
        }
        instance
    }
}

/**
 * Instance scope.
 *
 * Allows you to bind an instance with `bind<Type>() with instance( MyType() )`
 */
public fun <T : Any> Kodein.Builder.instance(instance: T) = CProvider("instance") { instance }
