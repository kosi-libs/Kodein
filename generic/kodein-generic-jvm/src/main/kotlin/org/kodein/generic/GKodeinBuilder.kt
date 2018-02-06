package org.kodein.generic

import org.kodein.Kodein
import org.kodein.SearchDSL
import org.kodein.bindings.Scope
import org.kodein.generic

/**
 * Starts the binding of a given type with a given tag.
 *
 * T generics will be kept.
 *
 * @param T The type to bind.
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.TypeBinder.with]) on it to finish the binding syntax and register the binding.
 */
inline fun <reified T : Any> Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = Bind<T>(generic(), tag, overrides)

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = Bind(tag, overrides)

/**
 * Binds the previously given tag to the given instance.
 *
 * T generics will be kept.
 *
 * @param T The type of value to bind.
 * @param value The instance to bind.
 */
infix inline fun <reified T : Any> Kodein.Builder.ConstantBinder.with(value: T) = With(generic(), value)

inline fun <reified T : Any> SearchDSL.binding(tag: Any? = null) = SearchDSL.Binding(generic<T>(), tag)

inline fun <reified T : Any> SearchDSL.context() = Context(generic<T>())

inline fun <reified T : Any> SearchDSL.scope(@Suppress("UNUSED_PARAMETER") scope: Scope<T, *>) = Context(generic<T>())

inline fun <reified T : Any> SearchDSL.argument() = Argument(generic<T>())
