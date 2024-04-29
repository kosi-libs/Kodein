@file:Suppress("unused", "unchecked_cast", "deprecation")

package org.kodein.di

import org.kodein.di.bindings.ArgSetBinding
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.InSet
import org.kodein.di.bindings.SetBinding
import org.kodein.di.bindings.TypeBinderInSet
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
public inline fun <reified T : Any> DI.Builder.bindSet(tag: Any? = null, overrides: Boolean? = null): Unit = Bind(
    tag = tag,
    overrides = overrides,
    binding = SetBinding(
        contextType = TypeToken.Any,
        _elementType = generic<T>(),
        createdType = erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>
    )
)

/**
 * Creates a set and add multiple bindings to it.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The builder that should add binding in the set.
 */
public inline fun <reified T : Any> DI.Builder.bindSet(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DI.Builder.SetBinder<T>.() -> Unit
): Unit = BindInSet(tag = tag, overrides = overrides, type = generic(), creator = creator)

/**
 * Add multiple bindings in an existing set
 *
 * T generics will be erased!
 *
 * @param T The binding type of the targeted set.
 * @param creator The builder that should add binding in the set.
 */
public inline fun <reified T : Any> DI.Builder.inBindSet(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DI.Builder.SetBinder<T>.() -> Unit
): Unit = InBindSet(tag = tag, overrides = overrides, type = generic(), creator = creator)

/**
 * Creates a set: multiple bindings can be added in this set.
 *
 * T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 */
public inline fun <reified A : Any, reified T : Any> DI.Builder.bindArgSet(
    tag: Any? = null,
    overrides: Boolean? = null
): Unit = Bind(
    tag = tag,
    overrides = overrides,
    binding = ArgSetBinding(
        contextType = TypeToken.Any,
        argType = generic<A>(),
        _elementType = generic<T>(),
        createdType = erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>
    )
)

/**
 * Creates a set and add multiple bindings to it.
 *
 * T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The builder that should add binding in the set.
 */
public inline fun <reified A : Any, reified T : Any> DI.Builder.bindArgSet(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DI.Builder.ArgSetBinder<A, T>.() -> Unit
): Unit = BindInArgSet(tag = tag, overrides = overrides, argType = generic(), type = generic(), creator = creator)

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
@Deprecated("Use inBindSet { add { BINDING } } instead.")
public inline fun <reified T : Any> DI.Builder.TypeBinder<T>.inSet(): TypeBinderInSet<T, Set<T>> =
    InSet(setTypeToken = erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>)

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
@Deprecated("Use inBindSet { add { BINDING } } instead.")
public inline fun <reified T : Any> DI.Builder.inSet(
    tag: Any? = null,
    overrides: Boolean? = null,
    creator: () -> DIBinding<*, *, T>
): Unit = BindSet(tag = tag, overrides = overrides, creator())

/**
 * Defines that the binding will be saved in a set binding.
 *
 * T generics will be erased!
 *
 * @param T The type of the binding.
 */
@Deprecated("Use inBindSet { add { BINDING } } instead.")
public inline fun <reified T : Any> DI.Builder.addInBindSet(
    tag: Any? = null,
    overrides: Boolean? = null,
    creator: () -> DIBinding<*, *, T>
): Unit = AddBindInSet(tag = tag, overrides = overrides, binding = creator())
