package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Kodein interface to be passed to factory scope methods.
 *
 * It is augmented to allow such methods to access a factory or instance from the binding it is overriding (if it is overriding).
 */
interface FactoryKodein : Kodein {

    /**
     * Gets a factory from the overridden binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance the returned factory creates or retrieves.
     * @return A factory yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <A, T : Any> overriddenFactory(): (A) -> T

    /**
     * Gets a factory from the overridden binding, if this binding overrides an existing binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance the returned factory creates or retrieves.
     * @return A factory yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <A, T : Any> overriddenFactoryOrNull(): ((A) -> T)?

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
    fun <A, T : Any> overriddenInstance(arg: A): T = overriddenFactory<A, T>().invoke(arg)

    /**
     * Gets an instance from the overridden factory binding, if this binding overrides an existing binding.
     *
     * @param A The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.
     * @param T The type of instance of this binding.
     * @param arg The argument to provide to the factory to retrieve or create an instance.
     * @return An instance yielded by the overridden factory binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun <A, T : Any> overriddenInstanceOrNull(arg: A): T? = overriddenFactoryOrNull<A, T>()?.invoke(arg)
}

/**
 * Base class that knows how to get an instance.
 *
 * All bindings are bound to a Factory.
 * Whether this factory creates a new instance at each call or not is left to implementation.
 *
 * @param A The type of argument used to create or retrieve an instance.
 * @param T The type of instance this factory creates or retrieves.
 */
interface Factory<in A, out T : Any> {

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
    fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: A): T

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
    val argType: Type

    /**
     * The type of object that is created by this factory.
     */
    val createdType: Type

    /**
     * The description of this factory (using simple type names), *used for debug print only*.
     */
    val description: String get() = "${factoryName()} { ${argType.simpleDispString} -> ${createdType.simpleDispString} } "

    /**
     * The description of this factory (using full type names), *used for debug print only*.
     */
    val fullDescription: String get() = "${factoryFullName()} { ${argType.fullDispString} -> ${createdType.fullDispString} } "
}

/**
 * Kodein interface to be passed to provider or instance scope methods.
 *
 * It is augmented to allow such methods to access a provider or instance from the binding it is overriding (if it is overriding).
 */
class ProviderKodein(private val _kodein: FactoryKodein) : Kodein by _kodein {

    /**
     * Gets a provider from the overridden binding.
     *
     * @param T The type of instance of this binding.
     * @return A provider yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <T : Any> overriddenProvider(): () -> T = _kodein.overriddenFactory<Unit, T>().toProvider { Unit }

    /**
     * Gets a provider from the overridden binding, if this binding overrides an existing binding.
     *
     * @param T The type of instance of this binding.
     * @return A provider yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <T : Any> overriddenProviderOrNull(): (() -> T)? = _kodein.overriddenFactoryOrNull<Unit, T>()?.toProvider { Unit }

    /**
     * Gets an instance from the overridden binding.
     *
     * @param T The type of instance of this binding.
     * @return An instance yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun <T : Any> overriddenInstance(): T = _kodein.overriddenInstance(Unit)

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @param T The type of instance of this binding.
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun <T : Any> overriddenInstanceOrNull(): T? = _kodein.overriddenInstanceOrNull(Unit)
}

/**
 * [Factory] specialization that has no argument.
 *
 * As a factory does need an argument, it uses `Unit` as its argument.
 */
interface Provider<out T: Any> : Factory<Unit, T> {

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
    override fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: Unit): T = getInstance(ProviderKodein(kodein), key)

    /**
     * Get an instance of type `T`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return an instance of `T`.
     */
    fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T

    override val argType: Type get() = Unit::class.java

    override val description: String get() = "${factoryName()} { ${createdType.simpleDispString} } "

    override val fullDescription: String get() = "${factoryName()} { ${createdType.fullDispString} } "

}
