package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.*


// attribute key for storing injector in a call
public val KodeinDIKey: AttributeKey<DI> = AttributeKey<DI>("KodeinDI")

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.ktor"))
public fun Application.di(): LazyDI = closestDI()

/**
 * Getting the global [DI] container from the [Application]
 */
public fun Application.closestDI() : LazyDI = LazyDI { attributes[KodeinDIKey] }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI(getApplication)","org.kodein.di.ktor"))
public fun di(getApplication: () -> Application) : LazyDI = closestDI(getApplication)

/**
 * Getting the global [DI] container from the [Application] parameter
 */
public fun closestDI(getApplication: () -> Application) : LazyDI = getApplication().closestDI()

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.ktor"))
public fun ApplicationCall.di(): LazyDI = closestDI { application }

/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun ApplicationCall.closestDI(): LazyDI = closestDI()

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.ktor"))
public fun Routing.di(): LazyDI = di { application }

/**
 * Getting the global [DI] container from the [Routing] feature
 */
public fun Routing.closestDI(): LazyDI = closestDI()

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.ktor"))
public fun Route.di(): LazyDI = closestDI()

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
        this is Routing -> di()
        else -> parent?.di() ?: throw IllegalStateException("No DI container found for [$this]")
    }
}

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.ktor"))
public fun PipelineContext<*, ApplicationCall>.di(): LazyDI = closestDI()


/**
 * Getting the global [DI] container from the [ApplicationCall]
 */
public fun PipelineContext<*, ApplicationCall>.closestDI(): LazyDI {
    val routingCall = (this.call as RoutingApplicationCall)
    return routingCall.route.di()
}
