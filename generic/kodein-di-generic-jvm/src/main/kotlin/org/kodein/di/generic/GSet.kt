@file:Suppress("unused")

package org.kodein.di.generic

import org.kodein.di.AnyToken
import org.kodein.di.Kodein
import org.kodein.di.bindings.ArgSetBinding
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.SetBinding
import org.kodein.di.generic

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> Kodein.Builder.setBinding() = SetBinding(AnyToken, generic<T>(), generic<Set<T>>())

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * A & T generics will be kept.
 *
 * @param A The argument type.
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified A, reified T: Any> Kodein.Builder.argSetBinding() = ArgSetBinding(AnyToken, generic<A>(), generic<T>(), generic<Set<T>>())

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be kept.
 *
 * @param T The type of the binding.
 */
inline fun <reified T: Any> Kodein.Builder.TypeBinder<T>.inSet() = InSet(generic())
