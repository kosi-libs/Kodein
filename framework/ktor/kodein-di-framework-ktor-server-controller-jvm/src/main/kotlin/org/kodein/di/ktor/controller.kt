package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*

/**
 * Base controller super class to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class ApplicationController(application: Application) : AbstractKodeinController(application) {
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
abstract class AbstractKodeinController(val application: Application) : KodeinController {
    override val kodein by kodein { application }
}

/**
 * Base controller interface to leverage your Ktor server as a MVC-like architecture
 *
 * Example:
 * class KodeinControllerImpl(application: Application) : KodeinController(application) {
 *    override val kodein by kodein { application }
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
interface KodeinController : KodeinAware {
    /**
     * Define the routes that may be applied by installing [KodeinControllerFeature]
     */
    fun Routing.installRoutes()
}