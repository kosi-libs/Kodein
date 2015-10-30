package com.github.salomonbrys.kodein

/**
 * Binds a type to a factory.
 */
public inline fun <reified A, T : Any> Kodein.Builder.factory(noinline factory: Kodein.(A) -> T): Factory<A, T> {
    val type = typeToken<A>()
    return CFactory({"factory<${type.dispName}>"}, factory, type)
}

/**
 * Binds a type to a provider.
 */
public fun <T : Any> Kodein.Builder.provider(provider: Kodein.() -> T) = CProvider({"provider"}, provider)

/**
 * Binds a type to a lazily instanciated singleton.
 */
public fun <T : Any> Kodein.Builder.singleton(creator: Kodein.() -> T): CProvider<T> {
    var instance: T? = null
    val lock = Any()

    return CProvider({"singleton"}) {
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
 * Binds a type to a lazily instanciated thread local singleton.
 */
public fun <T : Any> Kodein.Builder.threadSingleton(creator: Kodein.() -> T): CProvider<T> {
    val storage = ThreadLocal<T>()

    return CProvider({"threadSingleton"}) {
        var instance = storage.get()
        if (instance == null) {
            instance = creator()
            storage.set(instance)
        }
        instance
    }
}

/**
 * Binds a type to an instance.
 */
public fun <T : Any> Kodein.Builder.instance(instance: T) = CProvider({"instance"}) { instance }
