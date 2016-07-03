package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.KodeinImpl
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
 * See the file factories.kt for other scopes.
 */
interface Kodein : KodeinAwareBase {

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

    override val kodein: Kodein get() = this

    data class Bind(
            val type: Type,
            val tag: Any?
    ) {
        override fun toString() = "bind<${type.simpleDispString}>(${ if (tag != null) "\"$tag\"" else "" })"
    }

    data class Key(
            val bind: Bind,
            val argType: Type
    ) {
        override fun toString() = buildString {
            if (bind.tag != null) append("\"${bind.tag}\": ")
            if (argType != Unit.javaClass) append("(${argType.simpleDispString})") else append("()")
            append("-> ${bind.type.simpleDispString}")
        }
    }

    /**
     * Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`
     */
    class Builder internal constructor(val container: KodeinContainer.Builder, internal val _callbacks: MutableList<Kodein.() -> Unit>, init: Builder.() -> Unit) {

        inner class TBuilder {

            inner class TypeBinder<in T : Any> internal constructor(private val _bind: Bind, private val _overrides: Boolean?) {
                infix fun <R : T, A> with(factory: Factory<A, R>): Unit = container.bind(Key(_bind, factory.argType), _overrides) with factory
            }

            inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) {
                infix fun <A> from(factory: Factory<A, *>): Unit = container.bind(Key(Bind(factory.createdType, _tag), factory.argType), _overrides) with factory
            }

            inner class ConstantBinder internal constructor(private val _tag: Any, private val _overrides: Boolean?) {
                fun with(value: Any, debugType: Type) = container.bind(Key(Bind(value.javaClass, _tag), Unit.javaClass), _overrides) with CInstance(debugType, value)
                fun with(value: Any) = with(value, value.javaClass)
            }

            fun bind(type: Type, tag: Any? = null, overrides: Boolean? = null): TypeBinder<Any> = TypeBinder(Bind(type, tag), overrides)

            fun <T : Any> bind(type: TypeToken<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(Bind(type.type, tag), overrides)

            fun <T : Any> bind(type: Class<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(Bind(type, tag), overrides)

            fun bind(tag: Any? = null, overrides: Boolean? = null): DirectBinder = DirectBinder(tag, overrides)

            fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(tag, overrides)
        }

        val typed = TBuilder()

        init { init() }

        inner class ConstantBinder internal constructor(val binder: TBuilder.ConstantBinder) {
            infix inline fun <reified T : Any> with(value: T) = binder.with(value, typeToken<T>().type)
        }

        inline fun <reified T : Any> bind(tag: Any? = null, overrides: Boolean? = null): TBuilder.TypeBinder<T> = typed.bind(typeToken<T>(), tag, overrides)

        fun bind(tag: Any? = null, overrides: Boolean? = null): TBuilder.DirectBinder = typed.bind(tag, overrides)

        fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(typed.constant(tag, overrides))

        fun import(module: Kodein.Module, allowOverride: Boolean = false) {
            Builder(container.subBuilder(allowOverride, module.allowSilentOverride), _callbacks, module.init)
        }

        fun extend(kodein: Kodein, allowOverride: Boolean = false) = container.extend(kodein.container, allowOverride)

        fun onReady(f: Kodein.() -> Unit) {
            _callbacks += f
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
    class Module(val allowSilentOverride: Boolean = false, val init: Kodein.Builder.() -> Unit)

    val typed: TKodein

    val container: KodeinContainer

    companion object {

        operator fun invoke(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit): Kodein = KodeinImpl(allowSilentOverride, init)

    }

}

fun <A, T : Any> ((A) -> T).toProvider(arg: A): () -> T = { invoke(arg) }
