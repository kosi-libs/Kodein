package org.kodein.di

import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.Multiton
import org.kodein.di.bindings.RefMaker
import org.kodein.type.generic

/**
 * Creates a multiton: will create an instance on first request for each different argument
 * and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument.
 * Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T: Any> DI.BindBuilder.WithScope<C>.multiton(
    ref: RefMaker? = null,
    sync: Boolean = true,
    noinline creator: BindingDI<C>.(A) -> T
): Multiton<C, A, T> = Multiton(scope, contextType, explicitContext, generic(), generic(), ref, sync, creator)

/**
 * Binds a multiton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested.
 * Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified A : Any, reified T: Any> DI.Builder.bindMultiton(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline creator: DirectDI.(A) -> T
): Unit = Bind(tag = tag, overrides = overrides, binding = multiton(sync = sync, creator = creator))
