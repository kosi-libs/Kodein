package org.kodein.di.ktor

import io.ktor.routing.*
import org.kodein.di.*

/**
 * Extend the nearest [DI] container, Global (from the Application) or Local (from a parent)
 */
public inline fun Route.subDI(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) {
    // Get any DI container in the parent # avoid infinite loop / StackOverflowError
    val parentDI = parent?.di() ?: di { application }
    this.attributes.put(KodeinDIKey, subDI(parentDI, allowSilentOverride, copy, init))
}
