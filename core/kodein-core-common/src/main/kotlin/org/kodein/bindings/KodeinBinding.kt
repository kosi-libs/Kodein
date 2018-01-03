package org.kodein.bindings

import org.kodein.*

interface Binding<C, A, T: Any> {

    /**
     * Get an instance of type `T` function argument `A`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return The instance of the requested type.
     */
    fun getFactory(kodein: BindingKodein<C>, key: Kodein.Key<C, A, T>): (A) -> T
}

/**
 * Base class that knows how to get an instance.
 *
 * All bindings are bound to a Binding.
 * Whether this factory creates a new instance at each call or not is left to implementation.
 *
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

    val scope: Scope<C, *>? get() = null

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
}

inline fun <C, T: Any> simpleBindingProvider(crossinline f: NoArgBindingKodein<C>.() -> T) = object : Binding<C, Unit, T> {
    override fun getFactory(kodein: BindingKodein<C>, key: Kodein.Key<C, Unit, T>): (Unit) -> T = { NoArgBindingKodeinWrap(kodein).f() }
}

/**
 * [KodeinBinding] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface NoArgKodeinBinding<C, T: Any> : KodeinBinding<C, Unit, T>, Binding<C, Unit, T> {

    override val argType get() = UnitToken

}
