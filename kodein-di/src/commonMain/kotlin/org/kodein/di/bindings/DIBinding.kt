package org.kodein.di.bindings

import org.kodein.di.*
import org.kodein.type.TypeToken

/**
 * Base class that knows how to get an instance.
 *
 * All bindings are bound to a Binding.
 * Whether this factory creates a new instance at each call or not is left to implementation.
 *
 * @param C The type of the context used by the retriever.
 * @param A The type of argument used to create or retrieve an instance.
 * @param T The type of instance this factory creates or retrieves.
 */
interface Binding<C : Any, A, T: Any> {

    /**
     * Returns a factory for the given key.
     * A factory is a function that returns an instance of type `T` function argument `A`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param di: A DI instance (augmented for the binding) to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return The instance of the requested type.
     */
    fun getFactory(di: BindingDI<C>, key: DI.Key<C, A, T>): (A) -> T
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIBinding<C,A,T>"), DeprecationLevel.ERROR)
typealias KodeinBinding<C,A,T> = DIBinding<C,A,T>

/**
 * Binding that is registered inside a DI object.
 *
 * It is augmented to allow scoping, contextualizing, debugging, etc.
 *
 * @param C The type of the context used by the retriever.
 * @param A The type of argument used to create or retrieve an instance.
 * @param T The type of instance this factory creates or retrieves.
 */
interface DIBinding<C : Any, A, T : Any> : Binding<C, A, T> {

    /**
     * The name of this factory, *used for debug print only*.
     *
     * @return The simple name of this factory.
     */
    fun factoryName(): String

    /**
     * The full(er) name of this factory, *used for debug print only*.
     *
     * @return The full name of this factory.
     */
    fun factoryFullName(): String = factoryName()

    /**
     * The scope used by this factory, if any
     */
    val scope: Scope<C>? get() = null

    /**
     * The type of contexts that are to be set when using this factory.
     */
    val contextType: TypeToken<in C>

    /**
     * The type of the argument this factory will function for.
     */
    val argType: TypeToken<in A>

    /**
     * The type of object that is created by this factory.
     */
    val createdType: TypeToken<out T>

    /**
     * The description of this factory (using simple type names), *used for debug print only*.
     */
    val description: String get() {
        val arg = if (argType != TypeToken.Unit) "${argType.simpleDispString()} -> " else ""
        val scope = if (scope is NoScope) null else scope
        val context = scope?.let { "scoped(${TTOf(it).simpleDispString()})." } ?: if (contextType != TypeToken.Any) "contexted<${contextType.simpleDispString()}>()." else ""
        return "$context${factoryName()} { $arg${createdType.simpleDispString()} }"
    }

    /**
     * The description of this factory (using full type names), *used for debug print only*.
     */
    val fullDescription: String get() {
        val arg = if (argType != TypeToken.Unit) "${argType.qualifiedDispString()} -> " else ""
        val scope = if (scope is NoScope) null else scope
        val context = scope?.let { "scoped(${TTOf(it).qualifiedDispString()})." } ?: if (contextType != TypeToken.Any) "contexted<${contextType.qualifiedDispString()}>()." else ""
        return "$context${factoryFullName()} { $arg${createdType.qualifiedDispString()} }"
    }

    /**
     * An interface capable of copying a binding.
     *
     * Note that the copy **must** "reset" any reference or status of the binding.
     */
    interface Copier<C : Any, A, T: Any> {
        /**
         * Copy the binding this Copier is attached to.
         *
         * @param builder The builder used when copying, can be used to register hooks.
         * @return A copy of the binding.
         */
        fun copy(builder: DIContainer.Builder): DIBinding<C, A, T>

        companion object {
            /**
             * Util method to create a Copier.
             *
             * @param f The [Copier.copy] implementation.
             * @return A copier with the given implementation.
             */
            operator fun <C : Any, A, T: Any> invoke(f: (DIContainer.Builder) -> DIBinding<C, A, T>) = object : Copier<C, A, T> {
                override fun copy(builder: DIContainer.Builder) = f(builder)
            }
        }
    }

    /**
     * A copier that is responsible for copying / resetting the binding.
     * If null, it means that the binding **do not hold any reference or status** and need not be copied.
     */
    val copier: Copier<C, A, T>? get() = null

    /**
     * Whether this bindings supports subtype handling.
     */
    val supportSubTypes: Boolean get() = false
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("NoArgDIBinding<C,T>"), DeprecationLevel.ERROR)
typealias NoArgKodeinBinding<C,T> = NoArgDIBinding<C,T>
/**
 * [DIBinding] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface NoArgDIBinding<C : Any, T: Any> : DIBinding<C, Unit, T>, Binding<C, Unit, T> {

    override val argType get() = TypeToken.Unit

}
