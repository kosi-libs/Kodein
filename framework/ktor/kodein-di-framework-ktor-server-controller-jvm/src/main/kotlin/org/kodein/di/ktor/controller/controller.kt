package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*
import org.kodein.di.ktor.*

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
public abstract class AbstractDIController(private val application: Application) : DIController {
    public override val di: DI by di { application }
}

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