package org.kodein.di.ktor.controller

import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.*
import org.kodein.di.ktor.*


/**
 * Allow to install a [DIController] and defined [Route]s into the routing system
 * e.g. Route.controller { DIControllerImpl() }
 */
@ContextDsl
public fun Route.controller(init: DirectDI.() -> DIController): DIController = run {
    val diController by di().newInstance { init() }
    diController.apply { installRoutes() }
}

/**
 * Allow to install a [DIController] and defined [Route]s into the routing system
 * inside a specific route
 * e.g. Route.controller("/protected") { DIControllerImpl() }
 */
@ContextDsl
public fun Route.controller(endpoint: String, init: DirectDI.() -> DIController): Route = run {
    route(endpoint){
        controller(init)
    }
}