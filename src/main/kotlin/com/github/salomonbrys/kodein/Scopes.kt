package com.github.salomonbrys.kodein


public interface Scoped<out T : Any> {
    public fun getInstance(kodein: Kodein): T
    public val scopeName: String;
}

public class ScopedFactory<out T : Any>(override val scopeName: String, private val _factory: Kodein.() -> T) : Scoped<T> {
    override fun getInstance(kodein: Kodein) = kodein._factory()
}

public fun <T : Any> Kodein.Builder.factory(factory: Kodein.() -> T): Scoped<T> = ScopedFactory("Factory", factory)

/**
 * Singleton scope.
 *
 * Allows you to bind a lazily instanciated singleton with `bind<Type>() with singleton { MySingletonType() }`
 */
public fun <T : Any> Kodein.Builder.singleton(creator: Kodein.() -> T): Scoped<T> {
    var instance: T? = null
    val lock = Any()

    return ScopedFactory("Singleton") {
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
public fun <T : Any> Kodein.Builder.threadSingleton(creator: Kodein.() -> T): Scoped<T> {
    val storage = ThreadLocal<T>()

    return ScopedFactory("Thread Singleton") {
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
public fun <T : Any> Kodein.Builder.instance(instance: T): Scoped<T> = ScopedFactory("Instance") { instance }
