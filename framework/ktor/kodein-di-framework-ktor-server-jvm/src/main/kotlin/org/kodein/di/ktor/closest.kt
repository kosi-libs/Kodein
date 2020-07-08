package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.*


// attribute key for storing injector in a call
public val KodeinDIKey: AttributeKey<DI> = AttributeKey<DI>("KodeinDI")

/**
 * Getting the global [DI] container from the [Application]
 */
public fun Application.di(): LazyDI = LazyDI { attributes[KodeinDIKey] }

/**
 * Alias to `di`
 */
public fun Application.closestDI() : LazyDI = di()

/**
 * Getting the global [DI] container from the [Application] parameter
 */
public fun di(getApplication: () -> Application) : LazyDI = getApplication().di()

/**
 * Alias to `di`
 */
public fun closestDI(getApplication: () -> Application) : LazyDI = di(getApplication)

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun ApplicationCall.di(): LazyDI = di { application }

/**
 * Alias to `di`
 */
public fun ApplicationCall.closestDI(): LazyDI = di()

/**
 * Getting the global [DI] container from the [Routing] feature
 */
public fun Routing.di(): LazyDI = di { application }


/**
 * Alias to `di`
 */
public fun Routing.closestDI(): LazyDI = di()

/**
 * Getting the global or local (if extended) [DI] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [DI] container
 */
public fun Route.di(): LazyDI {
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
public fun Route.closestDI(): LazyDI = di()

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun PipelineContext<*, ApplicationCall>.di(): LazyDI {
    val routingCall = (this.call as RoutingApplicationCall)
    return routingCall.route.di()
}

/**
 * Alias to `di`
 */
public fun PipelineContext<*, ApplicationCall>.closestDI(): LazyDI = di()
