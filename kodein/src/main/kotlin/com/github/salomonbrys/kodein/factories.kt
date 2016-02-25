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
}

/**
 * Concrete implementation of factory that delegates `getInstance` to a factory method.
 */
class CFactory<A, out T : Any>(override val scopeName: String, override val argType: Type, private val _provider: Kodein.(A) -> T) : Factory<A, T> {
    override fun getInstance(kodein: Kodein, arg: A) = kodein._provider(arg)
}

/**
 * Concrete implementation of factory that delegates `getInstance` to a provider method.
 * A provider is a factory that takes no argument (Unit as it's argument type).
 */
class CProvider<out T : Any>(override val scopeName: String, private val _provider: Kodein.() -> T) : Factory<Unit, T> {
    override fun getInstance(kodein: Kodein, arg: Unit) = kodein._provider()
    override val argType: Type = Unit.javaClass
}
