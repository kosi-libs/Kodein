package org.kodein.di.ktor.controller

import io.ktor.server.application.Application
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.ktor.closestDI

/**
 * Base controller super class to leverage your Ktor server as an MVC-like architecture
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
public abstract class AbstractDIController(private val application: Application) : DIController {
    public override val di: DI by closestDI { application }
}

/**
 * Base controller interface to leverage your Ktor server as an MVC-like architecture
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
public interface DIController : DIAware {
    /**
     * Install the controller's routes into the [Routing] feature
     */
    public fun Route.installRoutes(): Unit = getRoutes()
    /**
     * Define the routes that may be applied into the [Routing] feature
     */
    public fun Route.getRoutes()
}