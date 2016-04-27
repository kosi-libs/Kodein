package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinContainer
import java.lang.reflect.Type

/**
 * KOtlin DEpendency INjection.
 *
 * To construct a Kodein instance, simply use it's block constructor and define your bindings in it :
 *
 * ```kotlin
 * val kodein = Kodein {
 *     bind<Type1>() with factory { arg: Arg -> ** provide a Type1 function arg ** }
 *     bind<Type2>() with provide { ** provide a Type1 ** }
 * }
 * ```
 *
 * See the file scopes.kt for other scopes.
 */
class Kodein internal constructor(val _container: KodeinContainer) {

    data class Bind(
            val type: Type,
            val tag: Any?
    ) {
        override fun toString() = "bind<${type.dispName}>(${ if (tag != null) "\"$tag\"" else "" })"
    }

    data class Key(
            val bind: Bind,
            val argType: Type
    ) {
        override fun toString() = buildString {
            if (bind.tag != null) append("\"${bind.tag}\": ")
            if (argType != Unit.javaClass) append("(${argType.dispName})") else append("()")
            append("-> ${bind.type.dispName}")
        }
    }

    /**
     * A module is used the same way as in the Kodein constructor :
     *
     * ```kotlin
     * val module = Kodein.Module {
     *     bind<Type2>() with provide { ** provide a Type1 ** }
     * }
     * ```
     */
    class Module(val allowSilentOverride: Boolean = false, val init: Builder.() -> Unit)

    internal enum class OverrideMode {
        ALLOW_SILENT {
            override val allow: Boolean get() = true
            override fun must(overrides: Boolean?) = overrides
            override fun allow(allowOverride: Boolean) = allowOverride
        },
        ALLOW_EXPLICIT {
            override val allow: Boolean get() = true
            override fun must(overrides: Boolean?) = overrides ?: false
            override fun allow(allowOverride: Boolean) = allowOverride
        },
        FORBID {
            override val allow: Boolean get() = false
            override fun must(overrides: Boolean?) = if (overrides != null && overrides) throw OverridingException("Overriding has been forbidden") else false
            override fun allow(allowOverride: Boolean) = if (allowOverride) throw OverridingException("Overriding has been forbidden") else false
        };

        abstract val allow: Boolean
        abstract fun must(overrides: Boolean?): Boolean?
        abstract fun allow(allowOverride: Boolean): Boolean

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

    /**
     * Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`
     */
    class Builder internal constructor(private val _overrideMode: OverrideMode, internal val _builder: KodeinContainer.Builder, init: Builder.() -> Unit) {

        init { init() }

        inner class TypeBinder<T : Any>(private val _bind: Bind, overrides: Boolean?) {
            private val _mustOverride = _overrideMode.must(overrides)
            infix fun <R : T, A> with(factory: Factory<A, R>) = _builder.bind(Key(_bind, factory.argType), factory, _mustOverride)
        }

        inner class ConstantBinder(private val _tag: Any, overrides: Boolean?) {
            private val _mustOverride = _overrideMode.must(overrides)
            infix fun with(value: Any) = _builder.bind(Key(Bind(value.javaClass, _tag), Unit.javaClass), instance(value), _mustOverride)
        }

        inline fun <reified T : Any> bind(tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(Bind(typeToken<T>(), tag), overrides)

        fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(tag, overrides)

        fun import(module: Module, allowOverride: Boolean = false) {
            Builder(OverrideMode.get(_overrideMode.allow(allowOverride), module.allowSilentOverride), _builder, module.init)
        }

        fun extend(kodein: Kodein, allowOverride: Boolean = false) {
            _builder.extend(kodein._container, _overrideMode.allow(allowOverride))
        }
    }

    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(KodeinContainer(Builder(OverrideMode.get(true, allowSilentOverride), KodeinContainer.Builder(), init)._builder))

    /**
     * This is for debug. It allows to print all binded keys.
     */
    val registeredBindings: Map<Kodein.Bind, String> get() = _container.registeredBindings

    val bindingsDescription: String get() = _container.bindingsDescription

    /**
     * Exception thrown when there is a dependency loop.
     */
    class DependencyLoopException internal constructor(message: String) : RuntimeException(message)

    /**
     * Exception thrown when asked for a dependency that cannot be found
     */
    class NotFoundException(message: String) : RuntimeException(message)

    /**
     * Exception thrown when there is an overriding error
     */
    class OverridingException(message: String) : RuntimeException(message)

    /**
     * Gets a factory for the given argument type, return type and tag.
     */
    inline fun <reified A, reified T : Any> factory(tag: Any? = null): ((A) -> T) = _container.nonNullFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>()))

    /**
     * Gets a factory for the given argument type, return type and tag, or null if non is found.
     */
    inline fun <reified A, reified T : Any> factoryOrNull(tag: Any? = null): ((A) -> T)? = _container.factoryOrNull<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>()))

    /**
     * Gets a provider for the given type and tag.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     */
    inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = _container.nonNullProvider(Kodein.Bind(typeToken<T>(), tag))

    /**
     * Gets a provider for the given type and tag, or null if none is found.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     */
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = _container.providerOrNull(Kodein.Bind(typeToken<T>(), tag))

    /**
     * Gets an instance for the given type and tag.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     */
    inline fun <reified T : Any> instance(tag: Any? = null): T = _container.nonNullProvider<T>(Kodein.Bind(typeToken<T>(), tag)).invoke()

    /**
     * Gets an instance for the given type and tag, or null if none is found.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     */
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = _container.providerOrNull<T>(Kodein.Bind(typeToken<T>(), tag))?.invoke()

    val java = JKodein(_container)
}

fun <A, T : Any> ((A) -> T).toProvider(arg: A): () -> T = { invoke(arg) }
