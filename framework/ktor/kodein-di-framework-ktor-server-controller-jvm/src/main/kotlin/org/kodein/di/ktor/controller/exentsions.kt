package org.kodein.di.ktor.controller

import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.kodein.di.DirectDI
import org.kodein.di.ktor.closestDI
import org.kodein.di.newInstance

/**
 * Allow to install a [DIController] and defined [Route]s into the routing system
 * e.g. Route.controller { DIControllerImpl() }
 */
public fun Route.controller(init: DirectDI.() -> DIController): DIController = run {
    val diController by closestDI().newInstance { init() }
    diController.apply { installRoutes() }
}

/**
 * Allow to install a [DIController] and defined [Route]s into the routing system
 * inside a specific route
 * e.g. Route.controller("/protected") { DIControllerImpl() }
 */
public fun Route.controller(endpoint: String, init: DirectDI.() -> DIController): Route = run {
    route(endpoint){
        controller(init)
    }
}