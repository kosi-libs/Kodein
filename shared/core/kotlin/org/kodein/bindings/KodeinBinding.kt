package org.kodein.bindings

import org.kodein.*

interface Binding<in A, T: Any> {

    /**
     * Get an instance of type `T` function argument `A`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @param arg: The argument to use to get the instance.
     * @return The instance of the requested type.
     */
    fun getFactory(kodein: BindingKodein, key: Kodein.Key<A, T>): (A) -> T
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
interface KodeinBinding<A, T : Any> : Binding<A, T> {

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
    val description: String get() = "${factoryName()} { ${argType.simpleDispString()} -> ${createdType.simpleDispString()} } "

    /**
     * The description of this factory (using full type names), *used for debug print only*.
     */
    val fullDescription: String get() = "${factoryFullName()} { ${argType.fullDispString()} -> ${createdType.fullDispString()} } "
}

interface NoArgBinding<T: Any> : Binding<Unit, T> {

    /**
     * Get an instance of type `T`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param bind: The key of the instance to get.
     * @return an instance of `T`.
     */
    fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T

    override fun getFactory(kodein: BindingKodein, key: Kodein.Key<Unit, T>): (Unit) -> T = getProvider(NoArgBindingKodeinWrap(kodein), key.bind).toUnitFactory()
}

inline fun <T: Any> simpleBindingProvider(crossinline f: NoArgBindingKodein.() -> T) = object : NoArgBinding<T> {
    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T = { kodein.f() }
}

/**
 * [KodeinBinding] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface NoArgKodeinBinding<T: Any> : KodeinBinding<Unit, T>, NoArgBinding<T> {

    override val argType get() = UnitToken

    override val description: String get() = "${factoryName()} { ${createdType.simpleDispString()} } "

    override val fullDescription: String get() = "${factoryFullName()} { ${createdType.fullDispString()} } "

}
