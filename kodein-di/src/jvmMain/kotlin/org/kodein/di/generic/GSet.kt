@file:Suppress("unused")

package org.kodein.di.generic

import org.kodein.di.DI
import org.kodein.di.bindings.ArgSetBinding
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.SetBinding
import org.kodein.di.generic
import org.kodein.type.TypeToken

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be kept.
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> DI.Builder.setBinding() = SetBinding(TypeToken.Any, org.kodein.type.generic<T>(), org.kodein.type.generic<Set<T>>())

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
inline fun <reified A : Any, reified T: Any> DI.Builder.argSetBinding() = ArgSetBinding(TypeToken.Any, org.kodein.type.generic<A>(), org.kodein.type.generic<T>(), org.kodein.type.generic<Set<T>>())

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be kept.
 *
 * @param T The type of the binding.
 */
inline fun <reified T: Any> DI.Builder.TypeBinder<T>.inSet() = InSet(org.kodein.type.generic())
