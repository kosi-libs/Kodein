package com.github.salomonbrys.kodein

/**
 * Singleton scope.
 *
 * Allows you to bind a lazily instanciated singleton with `bind<Type>() with singleton { MySingletonType() }`
 */
public fun <T : Any> Kodein.Builder.singleton(creator: (Kodein) -> T): (Kodein) -> T {
    var instance: T? = null
    val lock = Any()

    return { kodein ->
        if (instance != null)
            instance!!
        else
            synchronized(lock) {
                if (instance == null)
                    instance = creator(kodein)
                instance!!
            }
    }
}

/**
 * Thread singleton scope.
 *
 * Allows you to bind a lazily instanciated thread singleton with `bind<Type>() with threadSingleton { MySingletonType() }`
 */
public fun <T : Any> Kodein.Builder.threadSingleton(creator: (Kodein) -> T): (Kodein) -> T {
    val storage = ThreadLocal<T>()

    return { kodein ->
        var instance = storage.get()
        if (instance == null) {
            instance = creator(kodein)
            storage.set(instance)
        }
        instance!!
    }
}

/**
 * Instance scope.
 *
 * Allows you to bind an instance with `bind<Type>() with instance( MyType() )`
 */
public fun <T : Any> Kodein.Builder.instance(instance: T): (Kodein) -> T = { instance }
