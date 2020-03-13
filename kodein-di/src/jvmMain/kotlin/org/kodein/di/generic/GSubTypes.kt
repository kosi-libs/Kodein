package org.kodein.di.generic

import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.TypeBinderSubTypes
import org.kodein.di.generic
import org.kodein.type.TypeToken

/**
 * Allows to define a binding that will be called for any subtype of this type.
 *
 * First part of the `bind<Type>().subTypes() with { type -> binding }` syntax.
 *
 * @param T The parent type.
 * @param block A function that will give the binding for the provided sub-type.
 */
inline infix fun <reified C : Any, reified A : Any, reified T: Any> TypeBinderSubTypes<T>.with(noinline block: (TypeToken<out T>) -> DIBinding<in C, in A, out T>) = With<C, A>(org.kodein.type.generic(), org.kodein.type.generic(), org.kodein.type.generic(), block)
