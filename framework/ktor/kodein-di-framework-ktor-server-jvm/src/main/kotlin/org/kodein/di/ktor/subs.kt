package org.kodein.di.ktor

import io.ktor.routing.*
import org.kodein.di.*

inline fun Route.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) {
    // Get any Kodein container in the parent # avoid infinite loop / StackOverflowError
    val parentKodein = parent?.kodein() ?: kodein { application }
    this.attributes.put(KodeinKey, subKodein(parentKodein, allowSilentOverride, copy, init))
}