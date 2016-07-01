package com.github.salomonbrys.kodein

import java.lang.reflect.*
import java.util.*

/**
 * Container class where the bindings and their factories are stored.
 *
 * In kodein, every binding is stored as a factory (that's why a scope is a function creating a factory).
 * Providers are special classes of factories that take Unit as parameter.
 */
interface KodeinContainer {

    /**
     * This is for debug. It allows to print all binded keys.
     */
    val bindings: Map<Kodein.Key, Factory<*, *>>

    fun _notFoundException(reason: String): Kodein.NotFoundException
            = Kodein.NotFoundException("$reason\nRegistered in Kodein:\n" + bindings.description)

    /**
     * All Kodein getter methods, whether it's instance(), provider() or factory() eventually ends up calling this
     * function.
     */
    fun factoryOrNull(key: Kodein.Key): ((Any?) -> Any)?

    fun nonNullFactory(key: Kodein.Key): ((Any?) -> Any)
            = factoryOrNull(key) ?: throw _notFoundException("No factory found for $key")

    fun providerOrNull(bind: Kodein.Bind): (() -> Any)? {
        val factory = factoryOrNull(Kodein.Key(bind, Unit::class.java)) ?: return null
        return { factory(Unit) }
    }

    fun nonNullProvider(bind: Kodein.Bind): (() -> Any)
            = providerOrNull(bind) ?: throw _notFoundException("No provider found for $bind")


    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    class Builder internal constructor() {

        internal val _map: MutableMap<Kodein.Key, Factory<*, Any>> = HashMap()

        private fun _checkIsReified(key: Kodein.Key, type: Type) {
            when (type) {
                is TypeVariable<*> -> throw IllegalArgumentException("Binding $key uses a type variable named ${type.name}, therefore, the binded value can never be retrieved.")
                is ParameterizedType -> for (arg in type.actualTypeArguments) _checkIsReified(key, arg)
                is GenericArrayType -> _checkIsReified(key, type.genericComponentType)
                is WildcardType -> {
                    for (arg in type.lowerBounds)
                        _checkIsReified(key, arg)
                    for (arg in type.upperBounds)
                        _checkIsReified(key, arg)
                }
                is KodeinParameterizedType -> _checkIsReified(key, type.type)
                is Class<*> -> {}
                else -> throw IllegalArgumentException("Unknown type ${type.javaClass} $type")
            }
        }

        internal fun bind(key: Kodein.Key, factory: Factory<*, Any>, mustOverride: Boolean?) {
            if (mustOverride != null) {
                if (mustOverride && key !in _map)
                    throw Kodein.OverridingException("Binding $key must override an existing binding.")
                if (!mustOverride && key in _map)
                    throw Kodein.OverridingException("Binding $key must not override an existing binding.")
            }
            _checkIsReified(key, key.bind.type)
            _map[key] = factory
        }

        internal fun extend(container: KodeinContainer, allowOverride: Boolean) {
            if (allowOverride)
                _map.putAll(container.bindings)
            else
                container.bindings.forEach { bind(it.key, it.value, false) }
        }
    }


}
