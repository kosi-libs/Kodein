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
    class Builder internal constructor(allow: Boolean, silent: Boolean, internal val _map: MutableMap<Kodein.Key, Factory<*, Any>> = HashMap()) {

        private val _overrideMode = OverrideMode.get(allow, silent)

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

        private enum class OverrideMode {
            ALLOW_SILENT {
                override val isAllowed: Boolean get() = true
                override fun must(overrides: Boolean?) = overrides
                override fun checkMatch(allowOverride: Boolean) {}
            },
            ALLOW_EXPLICIT {
                override val isAllowed: Boolean get() = true
                override fun must(overrides: Boolean?) = overrides ?: false
                override fun checkMatch(allowOverride: Boolean) {}
            },
            FORBID {
                override val isAllowed: Boolean get() = false
                override fun must(overrides: Boolean?) = if (overrides != null && overrides) throw Kodein.OverridingException("Overriding has been forbidden") else false
                override fun checkMatch(allowOverride: Boolean) { if (allowOverride) throw Kodein.OverridingException("Overriding has been forbidden") }
            };

            abstract val isAllowed: Boolean
            abstract fun must(overrides: Boolean?): Boolean?
            abstract fun checkMatch(allowOverride: Boolean): Unit

            companion object {
                fun get(allow: Boolean, silent: Boolean): OverrideMode {
                    if (!allow)
                        return FORBID
                    if (silent)
                        return ALLOW_SILENT
                    return ALLOW_EXPLICIT
                }
            }
        }

        inner class Binder internal constructor(val key: Kodein.Key, overrides: Boolean?) {
            init {
                _checkIsReified(key, key.bind.type)

                val mustOverride = _overrideMode.must(overrides)

                if (mustOverride != null) {
                    if (mustOverride && key !in _map)
                        throw Kodein.OverridingException("Binding $key must override an existing binding.")
                    if (!mustOverride && key in _map)
                        throw Kodein.OverridingException("Binding $key must not override an existing binding.")
                }
            }

            infix fun with(factory: Factory<*, Any>) {
                _map[key] = factory
            }
        }

        fun bind(key: Kodein.Key, overrides: Boolean? = false): Binder = Binder(key, overrides)

        fun bind(bind: Kodein.Bind, overrides: Boolean? = false): Binder = bind(Kodein.Key(bind, Unit::class.java), overrides)

        fun extend(container: KodeinContainer, allowOverride: Boolean = false) {
            _overrideMode.checkMatch(allowOverride)
            if (allowOverride)
                _map.putAll(container.bindings)
            else
                container.bindings.forEach { bind(it.key) with it.value }
        }

        fun subBuilder(allowOverride: Boolean = false, allowSilentOverride: Boolean = false): Builder {
            _overrideMode.checkMatch(allowOverride)
            return Builder(allowOverride, allowSilentOverride, _map)
        }
    }


}
