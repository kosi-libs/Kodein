package org.kodein.di.ktor.controller

import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.*
import org.kodein.di.ktor.*


/**
 * Allow to install a [KodeinController] and defined [Route]s into the routing system
 * e.g. Route.controller { KodeinControllerImpl() }
 */
@ContextDsl
fun Route.controller(init: DKodein.() -> KodeinController) = run {
    val kodeinController by kodein().newInstance { init() }
    kodeinController.apply { installRoutes() }
}

/**
 * Allow to install a [KodeinController] and defined [Route]s into the routing system
 * inside a specific route
 * e.g. Route.controller("/protected") { KodeinControllerImpl() }
 */
@ContextDsl
fun Route.controller(endpoint: String, init: DKodein.() -> KodeinController) = run {
    route(endpoint){
        controller(init)
    }
}