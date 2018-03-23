@file:Suppress("unused")

package org.kodein.di.erased

import org.kodein.di.AnyToken
import org.kodein.di.Kodein
import org.kodein.di.bindings.ArgSetBinding
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.SetBinding
import org.kodein.di.erased
import org.kodein.di.erasedSet

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified T: Any> Kodein.Builder.setBinding() = SetBinding(AnyToken, erased<T>(), erasedSet<T>())

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
inline fun <reified A, reified T: Any> Kodein.Builder.argSetBinding() = ArgSetBinding(AnyToken, erased<A>(), erased<T>(), erasedSet<T>())

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
inline fun <reified T: Any> Kodein.Builder.TypeBinder<T>.inSet() = InSet(erasedSet())
