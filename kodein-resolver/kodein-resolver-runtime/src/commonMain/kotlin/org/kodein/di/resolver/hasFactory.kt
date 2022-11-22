package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.type.TypeToken
import org.kodein.type.generic

/**
 * Define whether a factory binding exists in the given DI container
 *
 * @param tag the bound tag, if any
 * @return true if the binding is found
 */
public inline fun <reified T: Any, reified A: Any> DI.hasFactory(tag: Any? = null): Boolean {
    val key = DI.Key(
        contextType = TypeToken.Any,
        argType = generic<A>(),
        type = generic<T>(),
        tag = tag
    )

    return container.tree.bindings[key] != null
}

/**
 * Define whether a provider binding exists in the given DI container
 *
 * @param tag the bound tag, if any
 * @return true if the binding is found
 */
public inline fun <reified T: Any> DI.hasProvider(tag: Any? = null): Boolean = hasFactory<T, Unit>(tag)
