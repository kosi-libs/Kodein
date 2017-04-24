package com.github.salomonbrys.kodein.bindings

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.TypeToken
import com.github.salomonbrys.kodein.UnitToken
import com.github.salomonbrys.kodein.toProvider

/**
 * Kodein interface to be passed to factory scope methods.
 *
 * It is augmented to allow such methods to access a factory or instance from the binding it is overriding (if it is overriding).
 *
 */
// Should be generic but cannot be because the Kotlin's type inference engine cannot infer a function's receiver type from it's return type.
// Ie, this class can be generic if/when the following code becomes legal in Kotlin:
//
// class Context<A, T>
// inline fun <reified A, reified T> foo(f: Context<A, T>.(A) -> T): T = TODO()
// fun test() { foo { a: Int -> "coucou $a" } }
@Kodein.KodeinDsl
interface BindingKodein : Kodein {

    /**
     * Gets a factory from the overridden binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance the returned factory creates or retrieves.
     * @return A factory yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactory(): (Any?) -> Any

    /**
     * Gets a factory from the overridden binding, if this binding overrides an existing binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance the returned factory creates or retrieves.
     * @return A factory yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactoryOrNull(): ((Any?) -> Any)?

    /**
     * Gets an instance from the overridden factory binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance of this binding.
     * @param arg The argument to provide to the factory to retrieve or create an instance.
     * @return An instance yielded by the overridden factory binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstance(arg: Any?): Any = overriddenFactory().invoke(arg)

    /**
     * Gets an instance from the overridden factory binding, if this binding overrides an existing binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance of this binding.
     * @param arg The argument to provide to the factory to retrieve or create an instance.
     * @return An instance yielded by the overridden factory binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstanceOrNull(arg: Any?): Any? = overriddenFactoryOrNull()?.invoke(arg)
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
interface Binding<in A, T : Any> {

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
    fun getInstance(kodein: BindingKodein, key: Kodein.Key<A, T>, arg: A): T

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

/**
 * Kodein interface to be passed to provider or instance scope methods.
 *
 * It is augmented to allow such methods to access a provider or instance from the binding it is overriding (if it is overriding).
 */
@Kodein.KodeinDsl
interface NoArgBindingKodein : Kodein {

    /**
     * Gets a provider from the overridden binding.
     *
     * @param T The type of instance of this binding.
     * @return A provider yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProvider(): () -> Any

    /**
     * Gets a provider from the overridden binding, if this binding overrides an existing binding.
     *
     * @param T The type of instance of this binding.
     * @return A provider yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProviderOrNull(): (() -> Any)?

    /**
     * Gets an instance from the overridden binding.
     *
     * @param T The type of instance of this binding.
     * @return An instance yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstance(): Any

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @param T The type of instance of this binding.
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstanceOrNull(): Any?
}

internal class NoArgBindingKodeinImpl(private val _kodein: BindingKodein) : NoArgBindingKodein, Kodein by _kodein {
    override fun overriddenProvider(): () -> Any = _kodein.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull(): (() -> Any)? = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
    override fun overriddenInstance(): Any = _kodein.overriddenInstance(Unit)
    override fun overriddenInstanceOrNull(): Any? = _kodein.overriddenInstanceOrNull(Unit)
}


/**
 * [Binding] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface NoArgBinding<T: Any> : Binding<Unit, T> {

    /**
     * Get an instance of type `T`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @param arg: A Unit argument that is ignored (a provider does not take arguments).
     * @return an instance of `T`.
     */
    override fun getInstance(kodein: BindingKodein, key: Kodein.Key<Unit, T>, arg: Unit): T = getInstance(NoArgBindingKodeinImpl(kodein), key)

    /**
     * Get an instance of type `T`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return an instance of `T`.
     */
    fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, T>): T

    override val argType: TypeToken<in Unit> get() = UnitToken

    override val description: String get() = "${factoryName()} { ${createdType.simpleDispString()} } "

    override val fullDescription: String get() = "${factoryName()} { ${createdType.fullDispString()} } "

}
