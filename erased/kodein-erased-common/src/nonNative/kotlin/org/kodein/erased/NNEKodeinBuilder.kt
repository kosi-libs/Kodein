package org.kodein.erased

import org.kodein.Kodein

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [Kodein.Builder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
fun Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null): Kodein.Builder.DirectBinder = Bind(tag, overrides)
