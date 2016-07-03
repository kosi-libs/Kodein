package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*


class ScopeRegistry {
    private val _cache = HashMap<Kodein.Bind, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getOrCreate(bind: Kodein.Bind, creator: () -> T): T {
        return _cache[bind] as T? ?: synchronized(_cache) {
            _cache.getOrPut(bind) { creator() } as T
        }
    }

    fun clear(): Unit = synchronized(_cache) { _cache.clear() }

    fun objects(): HashMap<Kodein.Bind, Any> = synchronized(_cache) { HashMap(_cache) }

    fun remove(bind: Kodein.Bind): Unit = synchronized(_cache) { _cache.remove(bind) }
}

interface Scope<in C> {
    fun getRegistry(key: Kodein.Key, context: C): ScopeRegistry
}

interface AutoScope<C> : Scope<C> {
    fun getContext(key: Kodein.Key) : C
}

/**
 * Base class for CScopedSingleton & CAutoScopedSingleton
 */
abstract class AScoped<in A, out C, out T : Any>(
        override val argType: Type,
        override val createdType: Type,
        override val factoryName: String,
        private val _creator: Kodein.(C) -> T
) : Factory<A, T> {

    val key = Any()

    @Suppress("UNCHECKED_CAST")
    override fun getInstance(kodein: Kodein, key: Kodein.Key, arg: A): T {
        val (context, registry) = _getCache(key, arg)
        return registry.getOrCreate(key.bind) { _creator(kodein, context) }
    }

    abstract protected fun _getCache(key: Kodein.Key, arg: A): Pair<C, ScopeRegistry>
}


/**
 * Binds a type to a scoped singleton, effectively creating a factory Scope -> Type
 */
class CScopedSingleton<C, out T : Any>(argType: Type, createdType: Type, private val _scope: Scope<C>, creator: Kodein.(C) -> T)
: AScoped<C, C, T>(argType, createdType, "scopedSingleton", creator)
{
    override fun _getCache(key: Kodein.Key, arg: C) = arg to _scope.getRegistry(key, arg)

    override val description: String get() = "$factoryName(${_scope.javaClass.simpleDispString}) { ${argType.simpleDispString} -> ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName(${_scope.javaClass.fullDispString}) { ${argType.fullDispString} -> ${createdType.fullDispString} } "
}

@Suppress("unused")
inline fun <reified C, reified T : Any> Kodein.Builder.scopedSingleton(scope: Scope<C>, noinline creator: Kodein.(C) -> T)
        = CScopedSingleton(typeToken<C>().type, typeToken<T>().type, scope, creator)



/**
 * Binds a type to an auto scoped singleton, effectively creating a provider of Type (since the scope is automagically provided)
 */
class CAutoScopedSingleton<out C, out T : Any>(createdType: Type, private val _scope: AutoScope<C>, creator: Kodein.(C) -> T)
: AScoped<Unit, C, T>(Unit::class.java, createdType, "autoScopedSingleton", creator)
{
    override fun _getCache(key: Kodein.Key, arg: Unit): Pair<C, ScopeRegistry> = _scope.getContext(key).let { it to _scope.getRegistry(key, it) }

    override val description: String get() = "$factoryName(${_scope.javaClass.simpleDispString}) { ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName(${_scope.javaClass.fullDispString}) { ${createdType.fullDispString} } "
}

@Suppress("unused")
inline fun <C, reified T : Any> Kodein.Builder.autoScopedSingleton(scope: AutoScope<C>, noinline creator: Kodein.(C) -> T)
        = CAutoScopedSingleton(typeToken<T>().type, scope, creator)

