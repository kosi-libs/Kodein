package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.SearchDSL
import org.kodein.di.erased

/**
 * Starts the binding of a given type with a given tag.
 *
 * T generics will be erased!
 *
 * @param T The type to bind.
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call `Kodein.Builder.TypeBinder.with` on it to finish the binding syntax and register the binding.
 */
inline fun <reified T : Any> Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null): Kodein.Builder.TypeBinder<T> = Bind(erased(), tag, overrides)

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
fun Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null): Kodein.Builder.DirectBinder = Bind(tag, overrides)

/**
 * Binds the previously given tag to the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of value to bind.
 * @param value The instance to bind.
 */
inline infix fun <reified T : Any> Kodein.Builder.ConstantBinder.with(value: T) = With(erased(), value)

/**
 * Creates a return type constrained spec.
 *
 * @property tag An optional tag constraint.
 */
@Suppress("unused")
inline fun <reified T : Any> SearchDSL.binding(tag: Any? = null) = SearchDSL.Binding(erased<T>(), tag)

/**
 * Creates a context constrained spec.
 */
inline fun <reified T> SearchDSL.context() = Context(erased<T>())

/**
 * Creates an argument constrained spec.
 */
inline fun <reified T> SearchDSL.argument() = Argument(erased<T>())
