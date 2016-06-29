package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinContainer
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.KClass

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
class Kodein internal constructor(internal val _container: KodeinContainer) : KodeinAwareBase {

    override val kodein = this

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
    class Builder internal constructor(private val _overrideMode: OverrideMode, internal val _builder: KodeinContainer.Builder, internal val _callbacks: MutableList<Kodein.() -> Unit>, init: Builder.() -> Unit) {

        init { init() }

        inner class TypeBinder<in T : Any> internal constructor(private val _bind: Bind, overrides: Boolean?) {
            private val _mustOverride = _overrideMode.must(overrides)
            infix fun <R : T, A> with(factory: Factory<A, R>) = _builder.bind(Key(_bind, factory.argType), factory, _mustOverride)
        }

        inner class DirectBinder internal constructor(private val _tag: Any?, overrides: Boolean?) {
            private val _mustOverride = _overrideMode.must(overrides)
            infix fun <A> from(factory: Factory<A, *>) = _builder.bind(Key(Bind(factory.createdType, _tag), factory.argType), factory, _mustOverride)
        }

        inner class ConstantBinder internal constructor(private val _tag: Any, overrides: Boolean?) {
            private val _mustOverride = _overrideMode.must(overrides)
            infix inline fun <reified T : Any> with(value: T) = with(value, typeToken<T>().type)
            fun with(value: Any, debugType: Type) = _builder.bind(Key(Bind(value.javaClass, _tag), Unit.javaClass), CInstance(debugType, value), _mustOverride)
        }

        fun bind(type: Type, tag: Any? = null, overrides: Boolean? = null): TypeBinder<Any> = TypeBinder(Bind(type, tag), overrides)

        fun <T : Any> bind(type: TypeToken<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(Bind(type.type, tag), overrides)

        fun <T : Any> bind(type: Class<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(Bind(type, tag), overrides)

        inline fun <reified T : Any> bind(tag: Any? = null, overrides: Boolean? = null) = bind(typeToken<T>(), tag, overrides)

        fun bind(tag: Any? = null, overrides: Boolean? = null) = DirectBinder(tag, overrides)

        fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(tag, overrides)

        fun import(module: Module, allowOverride: Boolean = false) {
            Builder(OverrideMode.get(_overrideMode.allow(allowOverride), module.allowSilentOverride), _builder, _callbacks, module.init)
        }

        fun extend(kodein: Kodein, allowOverride: Boolean = false) {
            _builder.extend(kodein._container, _overrideMode.allow(allowOverride))
        }

        fun onReady(f: Kodein.() -> Unit) {
            _callbacks += f
        }
    }

    private constructor(builder: Builder) : this(KodeinContainer(builder._builder)) {
        builder._callbacks.forEach { it() }
    }

    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(Builder(OverrideMode.get(true, allowSilentOverride), KodeinContainer.Builder(), ArrayList(), init))

    /**
     * This is for debug. It allows to print all binded keys.
     */
    val bindings: Map<Kodein.Key, Factory<*, *>> get() = _container.bindings

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


    val typed = TKodein(_container)


    companion object {
        // The companion object is empty but it exists to allow external libraries to extend it.
    }
}

fun <A, T : Any> ((A) -> T).toProvider(arg: A): () -> T = { invoke(arg) }

inline fun <reified T : Any, reified R : Any> T.instanceForClass(kodein: Kodein, tag: Any? = null): R = kodein.with(T::class as KClass<*>).instance<R>(tag)

inline fun <reified T : Any, reified R : Any> T.providerForClass(kodein: Kodein, tag: Any? = null): () -> R = kodein.with(T::class as KClass<*>).provider<R>(tag)
