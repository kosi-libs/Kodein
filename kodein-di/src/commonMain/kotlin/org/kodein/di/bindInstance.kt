package org.kodein.di

import org.kodein.di.bindings.InstanceBinding
import org.kodein.type.generic

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
public inline fun <reified T: Any> DI.Builder.instance(
    instance: T
): InstanceBinding<T> = InstanceBinding(generic(), instance)

/**
 * Binds an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param creator A function that must the instance of type T.
 */
public inline fun <reified T: Any> DI.Builder.bindInstance(
    tag: Any? = null,
    overrides: Boolean? = null,
    creator: () -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = instance(creator()))

/**
 * Binds a constant provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the constant.
 * @param creator A function that must the constant of type T.
 */
public inline fun <reified T: Any> DI.Builder.bindConstant(
    tag: Any,
    overrides: Boolean? = null,
    creator: () -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = instance(creator()))