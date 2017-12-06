package org.kodein

import org.kodein.bindings.*
import org.kodein.internal.KodeinImpl

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
interface Kodein : KodeinAware {

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
    class NotFoundException(val key: Kodein.Key<*, *, *>, message: String)
            : RuntimeException(message)

    /**
     * Exception thrown when there is an overriding error.
     *
     * @param message The message of the exception.
     */
    class OverridingException(message: String) : RuntimeException(message)

    /**
     * Defined only to conform to [KodeinAware].
     */
    override val kodein: Kodein get() = this

    /**
     * In Kodein, each [KodeinBinding] is bound to a Key. A Key holds all information necessary to retrieve a factory (and therefore an instance).
     *
     * @property bind The left part of the bind declaration.
     * @property argType The argument type of the associated factory (Will be `Unit` for a provider).
     */
    @Suppress("EqualsOrHashCode")
    data class Key<in C, in A, out T: Any>(
            val contextType: TypeToken<in C>,
            val argType: TypeToken<in A>,
            val type: TypeToken<out T>,
            val tag: Any?
    ) {

        /**
         * Because this type is immutable, we can cache its hash to make it faster inside a HashMap.
         */
        private var _hashCode: Int = 0

        /** @suppress */
        override fun hashCode(): Int {
            if (_hashCode == 0) {
                _hashCode = contextType.hashCode()
                _hashCode = 31 * _hashCode + argType.hashCode()
                _hashCode = 29 * type.hashCode()
                _hashCode = 23 * _hashCode + (tag?.hashCode() ?: 0)
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
        private fun StringBuilder._appendDescription(dispString: TypeToken<*>.() -> String) {
            append(" with ")
            if (contextType != UnitToken) {
                append("?<${contextType.dispString()}>().")
            }
            append("? { ")
            if (argType != UnitToken) {
                append(argType.dispString())
                append(" -> ")
            }
            append("? }")
        }


        /**
         * Description using simple type names. The description is as close as possible to the code used to create this bind.
         */
        val bindDescription: String get() = "bind<${type.simpleDispString()}>(${ if (tag != null) "tag = \"$tag\"" else "" })"

        /**
         * Description using full type names. The description is as close as possible to the code used to create this bind.
         */
        val bindFullDescription: String get() = "bind<${type.fullDispString()}>(${ if (tag != null) "tag = \"$tag\"" else "" })"

        /**
         * Description using simple type names. The description is as close as possible to the code used to create this key.
         */
        val description: String get() = buildString {
            append(bindDescription)
            _appendDescription(TypeToken<*>::simpleDispString)
        }

        /**
         * Description using full type names. The description is as close as possible to the code used to create this key.
         */
        val fullDescription: String get() = buildString {
            append(bindFullDescription)
            _appendDescription(TypeToken<*>::fullDispString)
        }
    }

    /**
     * Defines a kodein DSL function
     */
    @DslMarker
    annotation class KodeinDsl

    interface BindBuilder<C> {
        val contextType: TypeToken<C>
        interface Contexted<C> : BindBuilder<C> {
            class Impl<C>(override val contextType: TypeToken<C>) : Contexted<C>
        }
        interface Scoped<C> : BindBuilder<C> {
            val scope: Scope<C>
            class Impl<C>(override val contextType: TypeToken<C>, override val scope: Scope<C>) : Scoped<C>
        }
    }

    /**
     * Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`.
     *
     * Methods of this classes are really just proxies to the [KodeinContainer.Builder] methods.
     *
     * @property containerBuilder Every methods eventually ends up to a call to this builder.
     * @property _callbacks A list of callbacks that will be called once the [Kodein] object is constructed.
     */
    @KodeinDsl
    open class Builder internal constructor(
            internal val containerBuilder: KodeinContainer.Builder,
            internal val _callbacks: MutableList<DKodein.() -> Unit>,
            internal val _bindingCallbacks: MutableList<Pair<Key<Unit, *, *>, BindingKodein.() -> Unit>>
    ) : BindBuilder.Contexted<Any?>, BindBuilder.Scoped<Any?> {

        override val contextType = AnyToken

        override val scope: Scope<Any?> get() = NoScope() // Recreating a new NoScope every-time *on purpose*!

        /**
         * Left part of the type-binding syntax (`bind(type, tag)`).
         *
         * @param T The type to bind.
         * @param bind The type and tag object that will compose the key to bind.
         * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         */
        inner class TypeBinder<T : Any> internal constructor(val type: TypeToken<out T>, val tag: Any?, val overrides: Boolean?) {
            internal val containerBuilder get() = this@Builder.containerBuilder

            /**
             * Binds the previously given type and tag to the given binding.
             *
             * @param binding The binding to bind.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            infix fun <C, A> with(binding: KodeinBinding<in C, in A, out T>) = containerBuilder.bind(Kodein.Key(binding.contextType, binding.argType, type, tag), binding, overrides)
        }

        /**
         * Left part of the direct-binding syntax (`bind(tag)`).
         *
         * @property _tag The tag that is being bound.
         * @property _overrides Whether this bind **must**, **may** or **must not** override an existing binding.
         */
        inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) {
            /**
             * Binds the previously given tag to the given binding.
             *
             * The bound type will be the [KodeinBinding.createdType].
             *
             * @param binding The binding to bind.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            infix fun <C, A, T: Any> from(binding: KodeinBinding<in C, in A, out T>) = containerBuilder.bind(Kodein.Key(binding.contextType, binding.argType, binding.createdType, _tag), binding, _overrides)
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
             * @param T The type of value to bind.
             * @param value The instance to bind.
             * @param valueType The type to bind the instance to.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            fun <T: Any> With(valueType: TypeToken<out T>, value: T) = bind(tag = _tag) from InstanceBinding(valueType, value)
        }

        /**
         * Starts the binding of a given type with a given tag.
         *
         * @param T The type of value to bind.
         * @param type The type to bind.
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must** or **must not** override an existing binding.
         * @return The binder: call [TypeBinder.with]) on it to finish the binding syntax and register the binding.
         */
        fun <T : Any> Bind(type: TypeToken<out T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T> = TypeBinder(type, tag, overrides)

        /**
         * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must** or **must not** override an existing binding.
         * @return The binder: call [DirectBinder.from]) on it to finish the binding syntax and register the binding.
         */
        @PublishedApi
        internal fun bindDirect(tag: Any? = null, overrides: Boolean? = null): DirectBinder = DirectBinder(tag, overrides)

        /**
         * Starts a constant binding.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must** or **must not** override an existing binding.
         * @return The binder: call `with` on it to finish the binding syntax and register the binding.
         */
        fun constant(tag: Any, overrides: Boolean? = null) = ConstantBinder(tag, overrides)

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
        fun import(module: Kodein.Module, allowOverride: Boolean = false) {
            Builder(containerBuilder.subBuilder(allowOverride, module.allowSilentOverride), _callbacks, _bindingCallbacks).apply(module.init)
        }

        /**
         * Adds a callback that will be called once the Kodein object is configured and instantiated.
         *
         * @param cb The callback.
         */
        fun onReady(cb: DKodein.() -> Unit) {
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
        @Suppress("UNCHECKED_CAST")
        fun onFactoryReady(key: Kodein.Key<Unit, *, *>, cb: BindingKodein.() -> Unit) {
            _bindingCallbacks += key to cb
        }

//        /**
//         * Adds a callback that will be called once the Kodein object is configured and instantiated.
//         *
//         * The callback will be able to access the overridden provider binding.
//         *
//         * @param key The key that defines the overridden provider access.
//         * @param cb The callback.
//         */
//        fun <T: Any> onProviderReady(key: Kodein.Key<*, Unit, T>, cb: NoArgBindingKodein.() -> Unit) = onFactoryReady(key) { cb(NoArgBindingKodeinWrap(this)) }
    }

    class MainBuilder(allowSilentOverride: Boolean) : Builder(KodeinContainer.Builder(true, allowSilentOverride, HashMap()), ArrayList(), ArrayList()) {

        var externalSource: ExternalSource? = null

        /**
         * Imports all bindings defined in the given [Kodein] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the kodein argument will continue to exist only once.
         * Both kodein objects will share the same instance.
         *
         * Note that externalSource **will be overeridden** if defined in the extended Kodein.
         *
         * @param kodein The kodein object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this kodein overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        fun extend(kodein: Kodein, allowOverride: Boolean = false) {
            containerBuilder.extend(kodein.container, allowOverride)
            kodein.container.externalSource?.let { externalSource = it }
        }

    }

    /**
     * A module is constructed the same way as in [Kodein] is:
     *
     * ```kotlinprivate
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
     * Every methods eventually ends up to a call to this container.
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
        operator fun invoke(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Kodein = KodeinImpl(allowSilentOverride, init)

        /**
         * Creates a Kodein object but without directly calling onReady callbacks.
         *
         * Instead, returns both the kodein instance and the callbacks.
         * Note that the returned kodein object should not be used before calling the callbacks.
         *
         * This is an internal function that exists primarily to prevent Kodein.global recursion.
         */
        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Pair<Kodein, () -> Unit> = KodeinImpl.withDelayedCallbacks(allowSilentOverride, init)
    }

}

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = bindDirect(tag, overrides)
