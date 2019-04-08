package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.generic.*

const val ROUTE_VERSION = "/version"
const val ROUTE_AUTHOR = "/author"

class ApplicationController(application: Application) : AbstractKodeinController(application) {
    override fun Routing.installRoutes() {
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

class KodeinControllerImpl(application: Application) : KodeinController {
    override val kodein by kodein { application }
    override fun Routing.installRoutes() {
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