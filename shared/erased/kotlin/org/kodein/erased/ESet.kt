@file:Suppress("unused")

package org.kodein.erased

import org.kodein.*
import org.kodein.bindings.ArgSetBinding
import org.kodein.bindings.InSet
import org.kodein.bindings.SetBinding

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> Kodein.Builder.setBinding() = SetBinding<T>(erased(), erasedSet())

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified A, reified T: Any> Kodein.Builder.argSetBinding() = ArgSetBinding<A, T>(erased(), erased(), erasedSet())

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
inline fun <reified T: Any> Kodein.Builder.TypeBinder<T>.inSet() = InSet(erasedSet())
