package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.*


// attribute key for storing injector in a call
val KodeinDIKey = AttributeKey<DI>("KodeinDI")

/**
 * Getting the global [DI] container from the [Application]
 */
fun Application.di() = LazyDI { attributes[KodeinDIKey] }

/**
 * Alias to `di`
 */
fun Application.closestDI() = di()

/**
 * Getting the global [DI] container from the [Application] parameter
 */
fun di(getApplication: () -> Application) = getApplication().di()

/**
 * Alias to `di`
 */
fun closestDI(getApplication: () -> Application) = di(getApplication)

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
fun ApplicationCall.di() = di { application }

/**
 * Alias to `di`
 */
fun ApplicationCall.closestDI() = di()

/**
 * Getting the global [DI] container from the [Routing] feature
 */
fun Routing.di() = di { application }


/**
 * Alias to `di`
 */
fun Routing.closestDI() = di()

/**
 * Getting the global or local (if extended) [DI] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [DI] container
 */
fun Route.di(): LazyDI {
    // Is there an inner DI container for this Route ?
    val routeDI = this.attributes.getOrNull(KodeinDIKey)
    return when {
        routeDI != null -> routeDI as LazyDI
        this is Routing -> di()
        else -> parent?.di() ?: throw IllegalStateException("No DI container found for [$this]")
    }
}

/**
 * Alias to `di`
 */
fun Route.closestDI() = di()

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
fun PipelineContext<*, ApplicationCall>.di(): LazyDI {
    val routingCall = (this.call as RoutingApplicationCall)
    return routingCall.route.di()
}

/**
 * Alias to `di`
 */
fun PipelineContext<*, ApplicationCall>.closestDI() = di()
