package org.kodein.di

import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.Scope
import org.kodein.di.internal.DIImpl
import org.kodein.type.TypeToken

/**
 * KOtlin DEpendency INjection.
 *
 * To construct a DI instance, simply use [it's block constructor][DI.invoke] and define your bindings in it :
 *
 * ```kotlin
 * val di = DI {
 *     bind<Dice>() with factory { sides: Int -> RandomDice(sides) }
 *     bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
 *     bind<Random>() with provider { SecureRandom() }
 *     constant("answer") with "forty-two"
 * }
 * ```
 */
public interface DI : DIAware {

    /**
     * Exception thrown when there is a dependency loop.
     *
     * @param message The message of the exception.
     */
    public class DependencyLoopException internal constructor(message: String) : RuntimeException(message)

    /**
     * Exception thrown when asked for a dependency that cannot be found.
     *
     * @property key The key that was not found.
     * @param message The message of the exception.
     */
    public class NotFoundException(public val key: Key<*, *, *>, message: String)
            : RuntimeException(message)

    /**
     * Exception thrown when searching for bindings and none could be found.
     *
     * @property search The specs that lead to no result.
     * @param message The message of the exception.
     */
    public class NoResultException(public val search: SearchSpecs, message: String)
        : RuntimeException(message)

    /**
     * Exception thrown when there is an overriding error.
     *
     * @param message The message of the exception.
     */
    public class OverridingException(message: String) : RuntimeException(message)

    override val di: DI get() = this

    /**
     * In DI, each [DIBinding] is bound to a Key. A Key holds all information necessary to retrieve a factory (and therefore an instance).
     *
     * A key contains many types & a tag.
     * It defines the types of the values that will be passed to and retrieved from DI, not the values themselves.
     *
     * @param C The in context type.
     * @param A The in argument type (`Unit` for a provider).
     * @param T The out type.
     * @property contextType The in context type.
     * @property argType The in argument type (`Unit` for a provider).
     * @property type The out type.
     * @property tag The associated tag.
     */
    @Suppress("EqualsOrHashCode")
    public data class Key<in C : Any, in A, out T: Any>(
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
        override fun toString(): String = description

        /**
         * Right part of the description string.
         *
         * @param dispString a function that gets the display string for a type.
         */
        private fun StringBuilder.appendDescription(dispString: TypeToken<*>.() -> String) {
            append(" with ")
            if (contextType != TypeToken.Any) {
                append("?<${contextType.dispString()}>().")
            }
            append("? { ")
            if (argType != TypeToken.Unit) {
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
        val bindFullDescription: String get() = "bind<${type.qualifiedDispString()}>(${ if (tag != null) "tag = \"$tag\"" else "" })"

        /**
         * Description using simple type names. The description is as close as possible to the code used to create this key.
         */
        val description: String get() = buildString {
            append(bindDescription)
            appendDescription(TypeToken<*>::simpleDispString)
        }

        val internalDescription: String get() = "(context: ${contextType.simpleDispString()}, arg: ${argType.simpleDispString()}, type: ${type.simpleDispString()}, tag: $tag)"

        /**
         * Description using full type names. The description is as close as possible to the code used to create this key.
         */
        val fullDescription: String get() = buildString {
            append(bindFullDescription)
            appendDescription(TypeToken<*>::qualifiedDispString)
        }
    }

    /**
     * Defines a di DSL function
     */
    @DslMarker
    public annotation class DIDsl

    /**
     * Base builder DSL interface that allows to define scoped and context bindings.
     *
     * @param C The context type.
     */
    public interface BindBuilder<C : Any> {
        /**
         * The context type that will be used by all bindings that are defined in this DSL context.
         */
        public val contextType: TypeToken<C>

        /**
         * Used to define bindings with a context.
         *
         * @param C The context type.
         */
        public interface WithContext<C : Any> : BindBuilder<C> {
            /** @suppress */
            public class Impl<C : Any>(override val contextType: TypeToken<C>) : WithContext<C>
        }

        /**
         * Used to define bindings with a scope.
         *
         * @param C The scope's Context.
         */
        public interface WithScope<C : Any> : BindBuilder<C> {

            /**
             * The scope that will be used by all bindings that are defined in this DSL context.
             */
            public val scope: Scope<C>

            /** @suppress */
            public class Impl<C : Any>(override val contextType: TypeToken<C>, override val scope: Scope<C>) : WithScope<C>
        }
    }

    /**
     * Allows for the DSL inside the block argument of the constructor of `DI` and `DI.Module`.
     *
     * Methods of this classes are really just proxies to the [DIContainer.Builder] methods.
     *
     * @property containerBuilder Every methods eventually ends up to a call to this builder.
     */
    @DIDsl
    public interface Builder : BindBuilder.WithContext<Any>, BindBuilder.WithScope<Any> {

        public val containerBuilder: DIContainer.Builder

        /**
         * Left part of the type-binding syntax (`bind(type, tag)`).
         *
         * @param T The type to bind.
         */
        public interface TypeBinder<T : Any> {
            /**
             * Binds the previously given type and tag to the given binding.
             *
             * @param binding The binding to bind.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            public infix fun <C : Any, A> with(binding: DIBinding<in C, in A, out T>)
        }

        /**
         * Left part of the direct-binding syntax (`bind(tag)`).
         */
        public interface DirectBinder {
            /**
             * Binds the previously given tag to the given binding.
             *
             * The bound type will be the [DIBinding.createdType].
             *
             * @param binding The binding to bind.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            public infix fun <C : Any, A, T: Any> from(binding: DIBinding<in C, in A, out T>)
        }

        /**
         * Left part of the constant-binding syntax (`constant(tag)`).
         *
         */
        public interface ConstantBinder {
            /**
             * Binds the previously given tag to the given instance.
             *
             * @param T The type of value to bind.
             * @param value The instance to bind.
             * @param valueType The type to bind the instance to.
             * @throws OverridingException If this bindings overrides an existing binding and is not allowed to.
             */
            @Suppress("FunctionName")
            public fun <T: Any> With(valueType: TypeToken<out T>, value: T)
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
        @Suppress("FunctionName")
        public fun <T : Any> Bind(type: TypeToken<out T>, tag: Any? = null, overrides: Boolean? = null): TypeBinder<T>

        /**
         * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must** or **must not** override an existing binding.
         * @return The binder: call [DirectBinder.from]) on it to finish the binding syntax and register the binding.
         */
        @Suppress("FunctionName")
        public fun Bind(tag: Any? = null, overrides: Boolean? = null): DirectBinder

        /**
         * Starts a constant binding.
         *
         * @param tag The tag to bind.
         * @param overrides Whether this bind **must** or **must not** override an existing binding.
         * @return The binder: call `with` on it to finish the binding syntax and register the binding.
         */
        public fun constant(tag: Any, overrides: Boolean? = null): ConstantBinder

        /**
         * Imports all bindings defined in the given [DI.Module] into this builder's definition.
         *
         * Note that modules are *definitions*, they will re-declare their bindings in each di instance you use.
         *
         * @param module The module object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this module overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun import(module: Module, allowOverride: Boolean = false)

        /**
         * Imports all bindings defined in the given [DI.Module]s into this builder's definition.
         *
         * Note that modules are *definitions*, they will re-declare their bindings in each di instance you use.
         *
         * @param modules The module objects to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this module overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun importAll(vararg modules: Module, allowOverride: Boolean = false)

        /**
         * Imports all bindings defined in the given [DI.Module]s into this builder's definition.
         *
         * Note that modules are *definitions*, they will re-declare their bindings in each di instance you use.
         *
         * @param modules The module objects to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [OverridingException].
         * @throws OverridingException If this module overrides an existing binding and is not allowed to
         *                             OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun importAll(modules: Iterable<Module>, allowOverride: Boolean = false)

        /**
         * Like [import] but checks that will only import each module once.
         *
         * If the module has already been imported, nothing happens.
         *
         * Careful: this is checked by name. If two modules share the same name, only one will be imported!
         */
        public fun importOnce(module: Module, allowOverride: Boolean = false)

        /**
         * Adds a callback that will be called once the DI object is configured and instantiated.
         *
         * @param cb The callback.
         */
        public fun onReady(cb: DirectDI.() -> Unit)

        public fun RegisterContextTranslator(translator: ContextTranslator<*, *>)
    }

    /**
     * Builder to create a [DI] object.
     */
    public interface MainBuilder : Builder {

        /**
         * If true, exceptions thrown will contain qualified names.
         */
        public var fullDescriptionOnError: Boolean

        /**
         * The external source is repsonsible for fetching / creating a value when DI cannot find a matching binding.
         */
        public val externalSources: MutableList<ExternalSource>

        /**
         * Imports all bindings defined in the given [DI] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the di argument will continue to exist only once.
         * Both di objects will share the same instance.
         *
         * Note that externalSource **will be overeridden** if defined in the extended DI.
         *
         * @param di The di object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *   If it is not, overrides (even explicit) will throw an [OverridingException].
         * @param copy The copy specifications, that defines which bindings will be copied to the new container.
         *   All bindings from the extended container will be accessible in the new container, but only the copied bindings are able to access overridden bindings in this new container.
         *   By default, all bindings that do not hold references (e.g. not singleton or multiton) are copied.
         * @throws OverridingException If this di overrides an existing binding and is not allowed to
         *   OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun extend(di: DI, allowOverride: Boolean = false, copy: Copy = Copy.NonCached)

        /**
         * Imports all bindings defined in the given [DI] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the di argument will continue to exist only once.
         * Both di objects will share the same instance.
         *
         * Note that externalSource **will be overeridden** if defined in the extended DI.
         *
         * @param directDI The direct di object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *   If it is not, overrides (even explicit) will throw an [OverridingException].
         * @param copy The copy specifications, that defines which bindings will be copied to the new container.
         *   All bindings from the extended container will be accessible in the new container, but only the copied bindings are able to access overridden bindings in this new container.
         *   By default, all bindings that do not hold references (e.g. not singleton or multiton) are copied.
         * @throws OverridingException If this di overrides an existing binding and is not allowed to
         *   OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun extend(directDI: DirectDI, allowOverride: Boolean = false, copy: Copy = Copy.NonCached)
    }

    /**
     * A module is constructed the same way as in [DI] is:
     *
     * ```kotlinprivate
     * val module = DI.Module {
     *     bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
     * }
     * ```
     *
     * @property name The name of this module (for debug).
     * @property allowSilentOverride Whether this module is allowed to non-explicit overrides.
     * @property init The block of configuration for this module.
     */
    public data class Module(val name: String, val allowSilentOverride: Boolean = false, val prefix: String = "", val init: Builder.() -> Unit) {
        @Deprecated("You should name your modules, for debug purposes.", replaceWith = ReplaceWith("Module(\"module name\", allowSilentOverride, init)"))
        public constructor(allowSilentOverride: Boolean = false, init: Builder.() -> Unit) : this("", allowSilentOverride, "", init)
    }

    /**
     * Every methods eventually ends up to a call to this container.
     */
    public val container: DIContainer

    public companion object {

        /**
         * Creates a [DI] instance.
         *
         * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
         * @param init The block of configuration.
         * @return The new DI object, freshly created, and ready for hard work!
         */
        public operator fun invoke(allowSilentOverride: Boolean = false, init: MainBuilder.() -> Unit): DI = DIImpl(allowSilentOverride, init)

        /**
         * Creates a [DI] instance that will be lazily created upon first access.
         *
         * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
         * @param init The block of configuration.
         * @return A lazy property that will yield, when accessed, the new DI object, freshly created, and ready for hard work!
         */
        public fun lazy(allowSilentOverride: Boolean = false, init: MainBuilder.() -> Unit): LazyDI = LazyDI { DIImpl(allowSilentOverride, init) }

        /**
         * Creates a direct [DirectDI] instance that will be lazily created upon first access.
         *
         * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
         * @param init The block of configuration.
         * @return The new DirectDI object, freshly created, and ready for hard work!
         */
        public fun direct(allowSilentOverride: Boolean = false, init: MainBuilder.() -> Unit): DirectDI = DIImpl(allowSilentOverride, init).direct

        /**
         * Creates a DI object but without directly calling onReady callbacks.
         *
         * Instead, returns both the di instance and the callbacks.
         * Note that the returned di object should not be used before calling the callbacks.
         *
         * This is an **internal** function that exists primarily to prevent DI.global recursion.
         *
         * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
         * @param init The block of configuration.
         * @return a Pair with the DI object, and the callbacks function to call.
         *   Note that you *should not* use the DI object before calling the callbacks function.
         */
        public fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: MainBuilder.() -> Unit): Pair<DI, () -> Unit> = DIImpl.withDelayedCallbacks(allowSilentOverride, init)

        public fun from(modules: List<Module>): DI = DI {
            modules.forEach { import(it) }
        }

        public var defaultFullDescriptionOnError: Boolean = false
    }

}
