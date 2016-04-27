@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*

/**
 * Binds a type to a factory.
 */
inline fun <reified A, T : Any> Kodein.Builder.factory(noinline factory: Kodein.(A) -> T): CFactory<A, T> {
    val type = typeToken<A>()
    return CFactory("factory<${type.dispName}>", type, factory)
}

/**
 * Binds a type to a provider.
 */
fun <T : Any> Kodein.Builder.provider(provider: Kodein.() -> T) = CProvider("provider", provider)

/**
 * Binds a type to a lazily instanciated singleton.
 */
fun <T : Any> Kodein.Builder.singleton(creator: Kodein.() -> T): CProvider<T> {
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
 * Binds a type to a lazily instanciated thread local singleton.
 */
fun <T : Any> Kodein.Builder.threadSingleton(creator: Kodein.() -> T): CProvider<T> {
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
 * Binds a type to an instance.
 */
fun <T : Any> Kodein.Builder.instance(instance: T) = CProvider("instance") { instance }


class CScoped<in S : Any, C : Any, out T : Any>(override val argType: Type, override val scopeName: String, private val _getCache: (S) -> Pair<C, HashMap<Any, Any>>, private val _creator: Kodein.(C) -> T) : Factory<S, T> {

    val key = Any()

    @Suppress("UNCHECKED_CAST")
    override fun getInstance(kodein: Kodein, arg: S): T {
        val (fArg, cache) = _getCache(arg)
        return cache[key] as T? ?: synchronized(cache) {
            cache.getOrPut(key) { _creator(kodein, fArg) } as T
        }

    }
}

inline fun <reified S : Any, reified T : Any> Kodein.Builder.scopedSingleton(noinline getCache: (S) -> HashMap<Any, Any>, noinline creator: Kodein.(S) -> T): CScoped<S, S, T> {
    val argType = typeToken<S>()
    return CScoped(argType, "scopedSingleton<${argType.dispName}>", { it to getCache(it) }, creator)
}

inline fun <C : Any, reified T : Any> Kodein.Builder.autoScopedSingleton(noinline getCache: () -> Pair<C, HashMap<Any, Any>>, noinline creator: Kodein.(C) -> T)
        = CScoped<Unit, C, T>(Unit.javaClass, "autoScopedSingleton", { getCache() }, creator)

