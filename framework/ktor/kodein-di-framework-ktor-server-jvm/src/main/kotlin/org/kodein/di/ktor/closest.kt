package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.*


// attribute key for storing injector in a call
val KodeinKey = AttributeKey<Kodein>("kodein")

/**
 * Getting the global [Kodein] container from the [Application]
 */
val Application.kodein: Kodein
    get() = attributes[KodeinKey]

/**
 * Getting the global [Kodein] container from the [Application] parameter
 */
fun kodein(getApplication: () -> Application) = getApplication().kodein

/**
 * Getting the global [Kodein] container from the [ApplicationCall]
 */
val PipelineContext<*, ApplicationCall>.kodein: Kodein
    get() = kodein { context.application }

/**
 * Getting the global [Kodein] container from the [ApplicationCall]
 */
val ApplicationCall.kodein: Kodein
    get() = kodein { application }

/**
 * Getting the global [Kodein] container from the [Routing] feature
 */
val Routing.kodein: Kodein
    get() = kodein { application }

/**
 * Getting the global [Kodein] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [Kodein] container
 */
val Route.kodein: Kodein
    get() = when {
        this is Routing -> kodein
        else -> parent?.kodein ?: throw IllegalStateException("No kodein container found for [$this]")
    }