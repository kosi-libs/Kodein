package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.*

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
    override val di by di { application }
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