package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.Kodein.Builder.TBuilder
import com.github.salomonbrys.kodein.internal.KodeinImpl
import java.lang.reflect.Type

/**
 * KOtlin DEpendency INjection.
 *
 * To construct a Kodein instance, simply use [it's block constructor][Kodein.invoke] and define your bindings in it :
 *
 * ```kotlin
 * val kodein = Kodein {
 *     bind<Dice>() with factory { sides: Int -> RandomDice(sides) }
 *     bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
 *     bind<Random>() with provider { SecureRandom() }
 *     constant("answer") with "forty-two"
 * }
 * ```
 */
interface Kodein : KodeinAwareBase {

    /**
     * Exception thrown when there is a dependency loop.
     *
     * @param message The message of the exception.
     */
    class DependencyLoopException internal constructor(message: String) : RuntimeException(message)

    /**
     * Exception thrown when asked for a dependency that cannot be found.
     *
     * @property key The key that was not found.
     * @param message The message of the exception.
     */
    class NotFoundException(val key: Kodein.Key, message: String)
            : RuntimeException(message)

    /**
     * Exception thrown when there is an overriding error.
     *
     * @param message The message of the exception.
     */
    class OverridingException(message: String) : RuntimeException(message)

    /**
     * Defined only to conform to [KodeinAwareBase].
     */
    override val kodein: Kodein get() = this

    /**
     * Part of a [Key] that represents the left part of a bind declaration.
     *
     * @property type The type that is bound.
     * @property tag The optional tag.
     */
    @Suppress("EqualsOrHashCode")
    data class Bind(
            val type: Type,
            val tag: Any?
    ) {

        /**
         * Because this type is immutable, we can cache its hash to make it faster inside a HashMap.
         */
        private var _hashCode: Int = 0

        /** @suppress */
        override fun hashCode(): Int {
            if (_hashCode == 0) {
                _hashCode = type.hashCode()
                _hashCode = 31 * _hashCode + (tag?.hashCode() ?: 0)
            }
            return _hashCode
        }

        /**
         * @return The [description]
         */
        override fun toString() = description

        /**
         * Description using simple type names. The description is as close as possible to the code used to create this bind.
         */
        val description: String get() = "bind<${type.simpleDispString}>(${ if (tag != null) "\"$tag\"" else "" })"

        /**
         * Description using full type names. The description is as close as possible to the code used to create this bind.
         */
        val fullDescription: String get() = "bind<${type.fullDispString}>(${ if (tag != null) "\"$tag\"" else "" })"
    }

    /**
     * In Kodein, each [Factory] is bound to a Key. A Key holds all information necessary to retrieve a factory (and therefore an instance).
     *
     * @property bind The left part of the bind declaration.
     * @property argType The argument type of the associated factory (Will be `Unit` for a provider).
     */
    @Suppress("EqualsOrHashCode")
    data class Key(
            val bind: Bind,
            val argType: Type
    ) {

        /**
         * Because this type is immutable, we can cache its hash to make it faster inside a HashMap.
         */
        private var _hashCode: Int = 0

        /** @suppress */
        override fun hashCode(): Int {
            if (_hashCode == 0) {
                _hashCode = bind.hashCode()
                _hashCode = 29 * _hashCode + argType.hashCode()
            }
            return _hashCode
        }

        /**
         * @return The [description]
         */
        override fun toString() = description

        /**
         * Right part of the description string.
         *
         * @param dispString a function that gets the display string for a type. Can be [simpleDispString] or [fullDispString]
         */
        private fun StringBuilder._appendDescription(dispString: Type.() -> String) {
            append(" with ? { ")
            if (argType != Unit::class.java) {
                append(argType.dispString())
                append(" -> ")
            }
            append("? }")
        }

        /**
         * Description using simple type names. The description is as close as possible to the code used to create this key.
         */
        val description: String get() = buildString {
            append(bind.description)
            _appendDescription(Type::simpleDispString)
        }

        /**
         * Description using full type names. The description is as close as possible to the code used to create this key.
         */
        val fullDescription: String get() = buildString {
            append(bind.fullDescription)
            _appendDescription(Type::fullDispString)
        }
    }

    /**
     * Defines a kodein DSL function
     */
    @DslMarker
    annotation class KodeinDsl

    /**
     * Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`.
     *
     * Methods of this classes are really just proxies to the [TBuilder] or [KodeinContainer.Builder] methods.
     *
     * @property container Every methods, either in this or in [TBuilder] eventually ends up to a call to this builder.
     * @property _callbacks A list of callbacks that will be called once the [Kodein] object is constructed.
     */
    @KodeinDsl
    class Builder internal constructor(
            val container: KodeinContainer.Builder,
            internal val _callbacks: MutableList<Kodein.() -> Unit>,
            internal val _factoryCallbacks: MutableList<Pair<Key, FactoryKodein.() -> Unit>>,
            init: Builder.() -> Unit
    ) {

        /**
         * Class holding all typed API (meaning the API where you provide [Type], [TypeToken] or `Class` objects).
         */
        inner class TBuilder {

            /**
             * Left part of the type-binding syntax (`bind(type, tag)`).
             *
             * @param T The type to bind.
             * @property _binder The container binder to use to complete the binding.
             */
            inner class TypeBinder<in T : Any> internal constructor(private val _binder: KodeinContainer.Builder.BindBinder) {
                /**
                 * Binds the previously given type and tag to the given factory.
                 *
                 * @param R The real type the factory will return.
                 * @param factory The factory to bind.
                 * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
                 */
                infix fun <R : T> with(factory: Factory<*, R>): Unit = _binder with factory
            }

            /**
             * Left part of the direct-binding syntax (`bind(tag)`).
             *
             * @property _tag The tag that is being bound.
             * @property _overrides Whether this bind **must**, **may** or **must not** override an existing binding.
             */
            inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) {
                /**
                 * Binds the previously given tag to the given factory.
                 *
                 * The bound type will be the [Factory.createdType].
                 *
                 * @param factory The factory to bind.
                 * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
                 */
                infix fun from(factory: Factory<*, *>): Unit = container.bind(Bind(factory.createdType, _tag), _overrides) with factory
            }

            /**
             * Left part of the constant-binding syntax (`constant(tag)`).
             *
             * @property _tag The tag that is being bound.
             * @property _overrides Whether this bind **must**, **may** or **must not** override an existing binding.
             */
            inner class ConstantBinder internal constructor(private val _tag: Any, private val _overrides: Boolean?) {
                /**
                 * Binds the previously given tag to the given instance.
                 *
                 * @param value The instance to bind.
                 * @param valueType The type to bind the instance to.
                 * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
                 */
                fun with(value: Any, valueType: Type): Unit = container.bind(Bind(valueType, _tag), _overrides) with CInstance(valueType, value)

                /**
                 * Binds the previously given tag to the given instance.
                 *
                 * @param T The type of value to bind.
                 * @param value The instance to bind.
                 * @param valueType The type to bind the instance to.
                 * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
                 */
                fun <T> with(value: T, valueType: Class<T>): Unit = with(value as Any, valueType as Type)

                /**
                 * Binds the previously given tag to the given instance.
                 *
                 * @param T The type of value to bind.
                 * @param value The instance to bind.
                 * @param valueType The type to bind the instance to.
                 * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
                 */
                fun <T> with(value: T, valueType: TypeToken<T>): Unit = with(value as Any, valueType.type)
            }

            /**
             * Starts the binding of a given type with a given tag.
             *
             * @param type The type to bind.
             * @param tag The tag to bind.
             * @param overrides Whether this bind **must** or **must not** override an existing binding.
             * @return The binder: call [TypeBinder.with]) on it to finish the binding syntax and register the binding.
             */
            fun bind(type: Type, tag: Any? = null, overrides: Boolean? = null): TypeBinder<Any> = TypeBinder(container.bind(Bind(type, tag), overrides))

            /**
             * Starts the binding of a given type with a given tag.
             *
             * @param T The type of value to bind.
             * @param type The type to bind.
             * @param tag The tag to bind.
             * @param overrides Whether this bind **must** or **must not** override an existing binding.
             * @return The binder: call [TypeBinder.with]) on it to finish the binding syntax and register the binding.
             */
            fun <T : Any> bind(type: TypeToken<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(container.bind(Bind(type.type, tag), overrides))

            /**
             * Starts the binding of a given type with a given tag.
             *
             * @param T The type of value to bind.
             * @param type The type to bind.
             * @param tag The tag to bind.
             * @param overrides Whether this bind **must** or **must not** override an existing binding.
             * @return The binder: call [TypeBinder.with]) on it to finish the binding syntax and register the binding.
             */
            fun <T : Any> bind(type: Class<T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(container.bind(Bind(type, tag), overrides))

            /**
             * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
             *
             * @param tag The tag to bind.
             * @param overrides Whether this bind **must** or **must not** override an existing binding.
             * @return The binder: call [DirectBinder.from]) on it to finish the binding syntax and register the binding.
             */
            fun bind(tag: Any? = null, overrides: Boolean? = null): DirectBinder = DirectBinder(tag, overrides)

            /**
             * Starts a constant binding.
             *
             * @param tag The tag to bind.
             * @param overrides Whether this bind **must** or **must not** override an existing binding.
             * @return The binder: call `with` on it to finish the binding syntax and register the binding.
             */
            fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(tag, overrides)
        }

        /**
         * Allows to access all typed API (meaning the API where you provide [Type], [TypeToken] or `Class` objects).
         */
        val typed = TBuilder()

        init { init() }

        /**
         * Starts the binding of a given type with a given tag.
         *
         * T generics will be kept.
         *
         * @param T The type to bind.
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
         * @return The binder: call [TBuilder.TypeBinder.with]) on it to finish the binding syntax and register the binding.
         */
        inline fun <reified T : Any> bindGeneric(tag: Any? = null, overrides: Boolean? = null): TBuilder.TypeBinder<T> = typed.bind(genericToken<T>(), tag, overrides)

        /**
         * Starts the binding of a given type with a given tag.
         *
         * T generics will be erased!
         *
         * @param T The type to bind.
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
         * @return The binder: call [TBuilder.TypeBinder.with]) on it to finish the binding syntax and register the binding.
         */
        inline fun <reified T : Any> bindErased(tag: Any? = null, overrides: Boolean? = null): TBuilder.TypeBinder<T> = typed.bind(T::class.java, tag, overrides)

        /**
         * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
         * @return The binder: call [TBuilder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
         */
        fun bindDirect(tag: Any? = null, overrides: Boolean? = null): TBuilder.DirectBinder = typed.bind(tag, overrides)

        /**
         * Left part of the constant-binding syntax (`constant(tag)`).
         *
         * @property binder The typed binder to use to actually bind.
         */
        inner class ConstantBinder internal constructor(val binder: TBuilder.ConstantBinder) {

            /**
             * Binds the previously given tag to the given instance.
             *
             * T generics will be kept.
             *
             * @param T The type of value to bind.
             * @param value The instance to bind.
             */
            infix inline fun <reified T : Any> withGeneric(value: T) = binder.with(value, genericToken<T>().type)

            /**
             * Binds the previously given tag to the given instance.
             *
             * T generics will be erased!
             *
             * @param T The type of value to bind.
             * @param value The instance to bind.
             */
            infix inline fun <reified T : Any> withErased(value: T) = binder.with(value, genericToken<T>().type)
        }

        /**
         * Starts a constant binding.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
         * @return The binder: call [ConstantBinder.with]) on it to finish the binding syntax and register the binding.
         */
        fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder = ConstantBinder(typed.constant(tag, overrides))

        /**
         * Imports all bindings defined in the given [Kodein.Module] into this builder's definition.
         *
         * Note that modules are *definitions*, they will re-declare their bindings in each kodein instance you use.
         *
         * @param module The module object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this module overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        fun import(module: Kodein.Module, allowOverride: Boolean = false): Unit {
            Builder(container.subBuilder(allowOverride, module.allowSilentOverride), _callbacks, _factoryCallbacks, module.init)
        }

        /**
         * Imports all bindings defined in the given [Kodein] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the kodein argument will continue to exist only once.
         * Both kodein objects will share the same instance.
         *
         * @param kodein The kodein object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this kodein overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        fun extend(kodein: Kodein, allowOverride: Boolean = false): Unit = container.extend(kodein.container, allowOverride)

        /**
         * Adds a callback that will be called once the Kodein object is configured and instantiated.
         *
         * @param cb The callback.
         */
        fun onReady(cb: Kodein.() -> Unit) {
            _callbacks += cb
        }

        /**
         * Adds a callback that will be called once the Kodein object is configured and instantiated.
         *
         * The callback will be able to access the overridden factory binding.
         *
         * @param key The key that defines the overridden factory access.
         * @param cb The callback.
         */
        fun onFactoryReady(key: Kodein.Key, cb: FactoryKodein.() -> Unit) {
            _factoryCallbacks += key to cb
        }

        /**
         * Adds a callback that will be called once the Kodein object is configured and instantiated.
         *
         * The callback will be able to access the overridden provider binding.
         *
         * @param key The key that defines the overridden provider access.
         * @param cb The callback.
         */
        fun onProviderReady(key: Kodein.Key, cb: ProviderKodein.() -> Unit) = onFactoryReady(key) { cb(ProviderKodeinImpl(this)) }
    }

    /**
     * A module is constructed the same way as in [Kodein] is:
     *
     * ```kotlin
     * val module = Kodein.Module {
     *     bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
     * }
     * ```
     *
     * @property allowSilentOverride Whether this module is allowed to non-explicit overrides.
     * @property init The block of configuration for this module.
     */
    class Module(val allowSilentOverride: Boolean = false, val init: Kodein.Builder.() -> Unit)

    /**
     * Allows to access all typed API (meaning the API where you provide `Type`, `TypeToken` or `Class` objects).
     */
    val typed: TKodein

    /**
     * Every methods, either in this or in [TKodein] eventually ends up to a call to this container.
     */
    val container: KodeinContainer

    companion object {

        /**
         * Creates a [Kodein] instance.
         *
         * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
         * @param init The block of configuration.
         * @return The new Kodein object, freshly created, and ready for hard work!
         */
        operator fun invoke(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit): Kodein = KodeinImpl(allowSilentOverride, init)

        /**
         * Creates a Kodein object but without directly calling onReady callbacks.
         *
         * Instead, returns both the kodein instance and the callbacks.
         * Note that the returned kodein object should not be used before calling the callbacks.
         *
         * This is an internal function that exists primarily to prevent Kodein.global recursion.
         */
        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit): Pair<Kodein, () -> Unit> = KodeinImpl.withDelayedCallbacks(allowSilentOverride, init)
    }

}

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.TBuilder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = bindDirect(tag, overrides)

/**
 * Transforms a factory function into a provider function by currying the factory with the given argument.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object to retrieve.
 * @receiver The factory to curry.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return A provider function that, when called, will call the receiver factory with the given argument.
 */
inline fun <A, T : Any> ((A) -> T).toProvider(crossinline arg: () -> A): () -> T = { invoke(arg()) }
