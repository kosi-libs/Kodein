package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*
import org.kodein.di.ktor.*

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("AbstractDIController"), DeprecationLevel.ERROR)
typealias AbstractKodeinController = AbstractDIController

/**
 * Base controller super class to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class ApplicationController(application: Application) : AbstractDIController(application) {
 *    override fun Route.getRoutes() {
 *      route(ROUTE_VERSION) {
 *      get {
 *        val version: String by instance("version")
 *        call.respondText(version)
 *      }
 *    }
 * }
 *
 */
abstract class AbstractDIController(val application: Application) : DIController {
    override val di by di { application }
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIController"), DeprecationLevel.ERROR)
typealias KodeinController = DIController

/**
 * Base controller interface to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class DIControllerImpl(application: Application) : DIController(application) {
 *    override val di by di { application }
 *    override fun Route.getRoutes() {
 *      route(ROUTE_VERSION) {
 *      get {
 *        val version: String by instance("version")
 *        call.respondText(version)
 *      }
 *    }
 * }
 *
 */
interface DIController : DIAware {
    /**
     * Define the getRoutes that may be applied by installing [DIControllerFeature]
     */
    // Deprecated Since 6.4
    @Deprecated(message = "As [KodeinControllerFeature] will be deprectated we will not need this anymore",
            replaceWith = ReplaceWith("Route.installRoutes()"))
    fun Routing.installRoutes() = getRoutes()
    /**
     * Install the controller's routes into the [Routing] feature
     */
    fun Route.installRoutes() = getRoutes()
    /**
     * Define the routes that may be applied into the [Routing] feature
     */
    fun Route.getRoutes()
}