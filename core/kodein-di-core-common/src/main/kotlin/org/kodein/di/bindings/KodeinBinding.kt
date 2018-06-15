package org.kodein.di.bindings

import org.kodein.di.*

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
interface Binding<C, A, T: Any> {

    /**
     * Returns a factory for the given key.
     * A factory is a function that returns an instance of type `T` function argument `A`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance (augmented for the binding) to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return The instance of the requested type.
     */
    fun getFactory(kodein: BindingKodein<C>, key: Kodein.Key<C, A, T>): (A) -> T
}

/**
 * Binding that is registered inside a Kodein object.
 *
 * It is augmented to allow scoping, contextualizing, debugging, etc.
 *
 * @param C The type of the context used by the retriever.
 * @param A The type of argument used to create or retrieve an instance.
 * @param T The type of instance this factory creates or retrieves.
 */
interface KodeinBinding<C, A, T : Any> : Binding<C, A, T> {

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
    val scope: Scope<C, *, A>? get() = null

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
        val arg = if (argType != UnitToken) "${argType.simpleDispString()} -> " else ""
        val scope = if (scope is NoScope) null else scope
        val context = scope?.let { "scoped(${TTOf(it).simpleDispString()})." } ?: if (contextType != AnyToken) "contexted<${contextType.simpleDispString()}>()." else ""
        return "$context${factoryName()} { $arg${createdType.simpleDispString()} }"
    }

    /**
     * The description of this factory (using full type names), *used for debug print only*.
     */
    val fullDescription: String get() {
        val arg = if (argType != UnitToken) "${argType.fullDispString()} -> " else ""
        val scope = if (scope is NoScope) null else scope
        val context = scope?.let { "scoped(${TTOf(it).fullDispString()})." } ?: if (contextType != AnyToken) "contexted<${contextType.fullDispString()}>()." else ""
        return "$context${factoryFullName()} { $arg${createdType.fullDispString()} }"
    }

    /**
     * An interface capable of copying a binding.
     *
     * Note that the copy **must** "reset" any reference or status of the binding.
     */
    interface Copier<C, A, T: Any> {
        /**
         * Copy the binding this Copier is attached to.
         *
         * @param builder The builder used when copying, can be used to register hooks.
         * @return A copy of the binding.
         */
        fun copy(builder: KodeinContainer.Builder): KodeinBinding<C, A, T>

        companion object {
            /**
             * Util method to create a Copier.
             *
             * @param f The [Copier.copy] implementation.
             * @return A copier with the given implementation.
             */
            operator fun <C, A, T: Any> invoke(f: (KodeinContainer.Builder) -> KodeinBinding<C, A, T>) = object : Copier<C, A, T> {
                override fun copy(builder: KodeinContainer.Builder) = f(builder)
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

/**
 * [KodeinBinding] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface NoArgKodeinBinding<C, T: Any> : KodeinBinding<C, Unit, T>, Binding<C, Unit, T> {

    override val argType get() = UnitToken

}
