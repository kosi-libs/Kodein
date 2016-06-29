package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Base class that knows how to construct an instance.
 *
 * All scopes must return a factory.
 */
interface Factory<in A, out T : Any> {
    /**
     * Get an instance of type T function argument A.
     *
     * Whether it's a new instance or not entirely depends on implementation (scope).
     */
    fun getInstance(kodein: Kodein, arg: A): T

    /**
     * The name of the scope this factory represents.
     * For debug only.
     */
    val scopeName: String;

    /**
     * The type of the argument this factory will function for.
     */
    val argType: Type

    /**
     * The type of object that is created by this factory.
     */
    val createdType: Type

    val description: String
}

/**
 * Concrete implementation of factory that delegates `getInstance` to a factory method.
 */
abstract class AFactory<in A, out T : Any>(override val scopeName: String, override val argType: Type, override val createdType: Type) : Factory<A, T> {

    override val description: String get() = "$scopeName { ${argType.typeName} -> ${createdType.typeName} } "
}

/**
 * Concrete implementation of factory that delegates `getInstance` to a provider method.
 * A provider is a factory that takes no argument (Unit as it's argument type).
 */
abstract class AProvider<out T : Any>(override val scopeName: String, override val createdType: Type) : Factory<Unit, T> {

    override fun getInstance(kodein: Kodein, arg: Unit) = getInstance(kodein)

    abstract fun getInstance(kodein: Kodein): T

    override val argType: Type = Unit.javaClass

    override val description: String get() = "$scopeName { ${createdType.typeName} } "
}
