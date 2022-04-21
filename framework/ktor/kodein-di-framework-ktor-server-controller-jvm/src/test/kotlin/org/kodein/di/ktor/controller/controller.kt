package org.kodein.di.ktor.controller

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

const val ROUTE_VERSION = "/version"
const val ROUTE_AUTHOR = "/author"

class ApplicationController(application: Application) : AbstractDIController(application) {
    override fun Route.getRoutes() {
        route(ROUTE_VERSION) {
            get {
                val version: String by instance("version")
                call.respondText(version)
            }
        }
        route(ROUTE_AUTHOR) {
            get {
                val author: String by instance("author")
                call.respondText(author)
            }
        }
    }
}

class DIControllerImpl(application: Application) : DIController {
    override val di by closestDI { application }
    override fun Route.getRoutes() {
        route(ROUTE_VERSION) {
            get {
                val version: String by instance("version")
                call.respondText(version)
            }
        }
        route(ROUTE_AUTHOR) {
            get {
                val author: String by instance("author")
                call.respondText(author)
            }
        }
    }
}