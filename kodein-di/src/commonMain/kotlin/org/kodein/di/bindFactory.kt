package org.kodein.di

import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.Factory
import org.kodein.type.generic

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T: Any> DI.BindBuilder<C>.factory(
    noinline creator: BindingDI<C>.(A) -> T
): Factory<C, A, T> = Factory(contextType, generic(), generic(), creator)

/**
 * Binds a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
public inline fun <reified A : Any, reified T: Any> DI.Builder.bindFactory(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DirectDI.(A) -> T
): Unit = Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))
