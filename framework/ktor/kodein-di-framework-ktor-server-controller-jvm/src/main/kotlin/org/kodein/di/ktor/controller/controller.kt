package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*
import org.kodein.di.ktor.*

/**
 * Base controller super class to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class ApplicationController(application: Application) : AbstractKodeinController(application) {
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
@Deprecated(DEPRECATE_7X)
abstract class AbstractKodeinController(val application: Application) : KodeinController {
    override val kodein by kodein { application }
}

/**
 * Base controller interface to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class KodeinControllerImpl(application: Application) : KodeinController(application) {
 *    override val kodein by kodein { application }
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
@Deprecated(DEPRECATE_7X)
interface KodeinController : KodeinAware {
    /**
     * Define the getRoutes that may be applied by installing [KodeinControllerFeature]
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