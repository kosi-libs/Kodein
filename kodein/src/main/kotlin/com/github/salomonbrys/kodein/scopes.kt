@file:Suppress("unused")

package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*

/**
 * Binds a type to a factory.
 */
class CFactory<in A, out T : Any>(argType: Type, createdType: Type, val factory: Kodein.(A) -> T) : AFactory<A, T>("factory", argType, createdType) {
    override fun getInstance(kodein: Kodein, arg: A) = this.factory(kodein, arg)

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
abstract class ASingleton<out T : Any>(scopeName: String, createdType: Type, val creator: Kodein.() -> T) : AProvider<T>(scopeName, createdType) {

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

    override val description: String get() = "$scopeName ( ${createdType.typeName} ) "
}

inline fun <reified T : Any> Kodein.Builder.instance(instance: T) = CInstance(typeToken<T>().type, instance)



/**
 * Base class for CScopedSingleton & CAutoScopedSingleton
 */
abstract class AScoped<in S, C, out T : Any>(override val argType: Type, override val createdType: Type, override val scopeName: String, private val _getCache: (S) -> Pair<C, HashMap<Any, Any>>, private val _creator: Kodein.(C) -> T) : Factory<S, T> {

    val key = Any()

    @Suppress("UNCHECKED_CAST")
    override fun getInstance(kodein: Kodein, arg: S): T {
        val (fArg, cache) = _getCache(arg)
        return cache[key] as T? ?: synchronized(cache) {
            cache.getOrPut(key) { _creator(kodein, fArg) } as T
        }
    }
}



/**
 * Binds a type to a scoped singleton, effectively creating a factory Scope -> Type
 */
class CScopedSingleton<S, out T : Any>(argType: Type, createdType: Type, getCache: (S) -> HashMap<Any, Any>, creator: Kodein.(S) -> T) : AScoped<S, S, T>(argType, createdType, "scopedSingleton", { it to getCache(it) }, creator) {
    override val description: String get() = "$scopeName { ${argType.typeName} -> ${createdType.typeName} } "
}

inline fun <reified S, reified T : Any> Kodein.Builder.scopedSingleton(noinline getCache: (S) -> HashMap<Any, Any>, noinline creator: Kodein.(S) -> T)
        = CScopedSingleton(typeToken<S>().type, typeToken<T>().type, getCache, creator)



/**
 * Binds a type to an auto scoped singleton, effectively creating a provider of Type (since the scope is automagically provided)
 */
class CAutoScopedSingleton<C, out T : Any>(createdType: Type, getCache: () -> Pair<C, HashMap<Any, Any>>, creator: Kodein.(C) -> T) : AScoped<Unit, C, T>(Unit::class.java, createdType, "autoScopedSingleton", { getCache() }, creator) {
    override val description: String get() = "$scopeName { ${createdType.typeName} } "
}

inline fun <C, reified T : Any> Kodein.Builder.autoScopedSingleton(noinline getCache: () -> Pair<C, HashMap<Any, Any>>, noinline creator: Kodein.(C) -> T)
        = CAutoScopedSingleton(typeToken<T>().type, getCache, creator)

