@file:Suppress("unused", "UNCHECKED_CAST")

package org.kodein.di

import org.kodein.di.bindings.*
import org.kodein.type.TypeToken
import org.kodein.type.erasedComp
import org.kodein.type.generic

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
@Deprecated("'bind() from ...' has been deprecated", replaceWith = ReplaceWith("bindSet<T>()"))
public inline fun <reified T: Any> DI.Builder.setBinding(): SetBinding<Any, T> = SetBinding(TypeToken.Any, generic<T>(), erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>)

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
@Deprecated("'bind() from ...' has been deprecated", replaceWith = ReplaceWith("bindArgSet<A, T>()"))
public inline fun <reified A : Any, reified T: Any> DI.Builder.argSetBinding(): ArgSetBinding<Any, A, T> = ArgSetBinding(TypeToken.Any, generic<A>(), generic<T>(), erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>)

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
public inline fun <reified T: Any> DI.Builder.bindSet(tag: Any? = null, overrides: Boolean? = null): Unit = Bind(tag = tag, overrides = overrides, binding = SetBinding(TypeToken.Any, generic<T>(), erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>))

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @return A set binding ready to be bound.
 */
@Suppress("RemoveExplicitTypeArguments")
public inline fun <reified A : Any, reified T: Any> DI.Builder.bindArgSet(tag: Any? = null, overrides: Boolean? = null): Unit = Bind(tag = tag, overrides = overrides, binding = ArgSetBinding(TypeToken.Any, generic<A>(), generic<T>(), erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>))

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
public inline fun <reified T: Any> DI.Builder.TypeBinder<T>.inSet(): TypeBinderInSet<T, Set<T>> = InSet(erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>)

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
public inline fun <reified T: Any> DI.Builder.inSet(tag: Any? = null, overrides: Boolean? = null, creator: () -> DIBinding<*, *, T>): Unit = BindSet(tag = tag, overrides = overrides, creator())
