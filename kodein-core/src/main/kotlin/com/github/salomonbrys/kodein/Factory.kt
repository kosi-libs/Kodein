package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Base class that knows how to get an instance.
 *
 * All bindings are bound to a Factory.
 * Whether this factory creates a new instance at each call or not is left to implementation.
 *
 * @param A The type of argument used to create or retrieve an instance.
 * @param T The type instance this factory creates or retrieves.
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
    fun getInstance(kodein: Kodein, key: Kodein.Key, arg: A): T

    /**
     * The name of this factory, *used for debug print only*.
     */
    val factoryName: String

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
    val description: String

    /**
     * The description of this factory (using full type names), *used for debug print only*.
     */
    val fullDescription: String
}

/**
 * Factory base.
 *
 * Enables sub-classes to implement only [Factory.getInstance].
 *
 * @param A The factory argument type.
 * @param T The created type.
 */
abstract class AFactory<in A, out T : Any>(override val factoryName: String, override val argType: Type, override val createdType: Type) : Factory<A, T> {

    override val description: String get() = "$factoryName { ${argType.simpleDispString} -> ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName { ${argType.fullDispString} -> ${createdType.fullDispString} } "
}

/**
 * Provider base.
 *
 * A provider is like a [AFactory], but without argument (the [Factory] is registered with a `Unit` argument).
 *
 * @param T The created type.
 */
abstract class AProvider<out T : Any>(override val factoryName: String, override val createdType: Type) : Factory<Unit, T> {

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
    override fun getInstance(kodein: Kodein, key: Kodein.Key, arg: Unit): T = getInstance(kodein, key)

    /**
     * Get an instance of type `T`.
     *
     * Whether it's a new instance or not entirely depends on implementation.
     *
     * @param kodein: A Kodein instance to use for transitive dependencies.
     * @param key: The key of the instance to get.
     * @return an instance of `T`.
     */
    abstract fun getInstance(kodein: Kodein, key: Kodein.Key): T

    override val argType: Type = Unit::class.java

    override val description: String get() = "$factoryName { ${createdType.simpleDispString} } "
    override val fullDescription: String get() = "$factoryName { ${createdType.fullDispString} } "
}
