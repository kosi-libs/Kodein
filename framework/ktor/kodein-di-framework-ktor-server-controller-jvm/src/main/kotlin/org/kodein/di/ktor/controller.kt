package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*

/**
 * Base controller to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class ApplicationController(application: Application) : KodeinController(application) {
 *    override fun Routing.installRoutes() {
 *      route(ROUTE_VERSION) {
 *      get {
 *        val version: String by instance("version")
 *        call.respondText(version)
 *      }
 *    }
 * }
 *
 */
abstract class KodeinController(val application: Application) : KodeinAware {
    override val kodein by kodein { application }
    /**
     * Define the routes that may be applied by installing [KodeinControllerFeature]
     */
    abstract fun Routing.installRoutes()
}