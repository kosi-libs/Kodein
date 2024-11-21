package org.kodein.di.ktor

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.kodein.di.DI
import org.kodein.di.LazyDI


// attribute key for storing injector in a call
public val KodeinDIKey: AttributeKey<DI> = AttributeKey<DI>("KodeinDI")

/**
 * Getting the global [DI] container from the [Application]
 */
public fun Application.closestDI() : LazyDI = LazyDI { attributes[KodeinDIKey] }

/**
 * Getting the global [DI] container from the [Application] parameter
 */
public fun closestDI(getApplication: () -> Application) : LazyDI = getApplication().closestDI()

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun ApplicationCall.closestDI(): LazyDI = closestDI { application }

/**
 * Getting the global [DI] container from the [Routing] feature
 */
public fun Routing.closestDI(): LazyDI = closestDI { application }

/**
 * Getting the global or local (if extended) [DI] container from the current [Route]
 * by browsering all the routing tree until we get to the root level, the [Routing] feature
 *
 * @throws IllegalStateException if there is no [DI] container
 */
public fun Route.closestDI(): LazyDI {
    // Is there an inner DI container for this Route ?
    val routeDI = this.attributes.getOrNull(KodeinDIKey)
    return when {
        routeDI != null -> routeDI as LazyDI
        this is Routing -> closestDI()
        else -> parent?.closestDI() ?: throw IllegalStateException("No DI container found for [$this]")
    }
}

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun RoutingContext.closestDI(): LazyDI {
    return call.route.closestDI()
}
