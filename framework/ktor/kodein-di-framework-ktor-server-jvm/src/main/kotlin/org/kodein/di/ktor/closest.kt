package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.*


// attribute key for storing injector in a call
@Deprecated(DEPRECATE_7X)
val KodeinKey = AttributeKey<Kodein>("kodein")

/**
 * Getting the global [Kodein] container from the [Application]
 */
@Deprecated(DEPRECATE_7X)
fun Application.kodein() = LazyKodein { attributes[KodeinKey] }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Application.closestKodein() = kodein()

/**
 * Getting the global [Kodein] container from the [Application] parameter
 */
@Deprecated(DEPRECATE_7X)
fun kodein(getApplication: () -> Application) = getApplication().kodein()

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun closestKodein(getApplication: () -> Application) = kodein(getApplication)

/**
 * Getting the global [Kodein] container from the [ApplicationCall]
 */
@Deprecated(DEPRECATE_7X)
fun ApplicationCall.kodein() = kodein { application }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun ApplicationCall.closestKodein() = kodein()

/**
 * Getting the global [Kodein] container from the [Routing] feature
 */
@Deprecated(DEPRECATE_7X)
fun Routing.kodein() = kodein { application }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Routing.closestKodein() = kodein()

/**
 * Getting the global or local (if extended) [Kodein] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [Kodein] container
 */
@Deprecated(DEPRECATE_7X)
fun Route.kodein(): LazyKodein {
    // Is there an inner Kodein container for this Route ?
    val routeKodein = this.attributes.getOrNull(KodeinKey)
    return when {
        routeKodein != null -> routeKodein as LazyKodein
        this is Routing -> kodein()
        else -> parent?.kodein() ?: throw IllegalStateException("No kodein container found for [$this]")
    }
}

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Route.closestKodein() = kodein()

/**
 * Getting the global [Kodein] container from the [ApplicationCall]
 */
@Deprecated(DEPRECATE_7X)
fun PipelineContext<*, ApplicationCall>.kodein(): LazyKodein {
    val routingCall = (this.call as RoutingApplicationCall)
    return routingCall.route.kodein()
}

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun PipelineContext<*, ApplicationCall>.closestKodein() = kodein()
