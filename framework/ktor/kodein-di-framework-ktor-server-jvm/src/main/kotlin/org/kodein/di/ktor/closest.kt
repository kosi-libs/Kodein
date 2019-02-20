package org.kodein.di.ktor

import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.routing.Route
import io.ktor.routing.Routing
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelineContext
import org.kodein.di.Kodein


// attribute key for storing injector in a call
val KodeinKey = AttributeKey<Kodein>("kodein")

/**
 * Getting the global [Kodein] container from the [Application]
 */
fun Application.kodein() = attributes[KodeinKey]

/**
 * Getting the global [Kodein] container from the [ApplicationCall]
 */
fun PipelineContext<*, ApplicationCall>.kodein() = context.application.kodein()

/**
 * Getting the global [Kodein] container from the [Routing] feature
 */
fun Routing.kodein() = application.kodein()

/**
 * Getting the global [Kodein] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [Kodein] container
 */
fun Route.kodein(): Kodein {
    val kodein = when (this) {
        is Routing -> kodein()
        else -> parent?.kodein()
    }

    return kodein ?: throw IllegalStateException("No kodein container found for [$this]")
}