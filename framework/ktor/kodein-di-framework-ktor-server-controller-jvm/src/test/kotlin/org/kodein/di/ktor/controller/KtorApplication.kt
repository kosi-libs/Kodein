package org.kodein.di.ktor.controller

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import org.kodein.di.with

const val AUTHOR = "Romain Boisselle"
const val VERSION = "1.0.0"

fun Application.installDI() = di {
          bind<ApplicationController>() with singleton { ApplicationController(instance()) }
          constant("author") with AUTHOR
          constant("version") with VERSION
        }

fun Application.diAbsControllerSuccess() {
    install(DefaultHeaders)
    installDI()
}

fun Application.diControllerImplSuccess() {
    install(DefaultHeaders)
    installDI()
}

fun Application.diControllerSuccess() {
    install(DefaultHeaders)
    installDI()
    routing {

        controller { DIControllerImpl(instance()) }

        route("/first") {
            controller { DIControllerImpl(instance()) }

            route("/second") {
                controller { ApplicationController(instance()) }

                route("/third") {
                    controller { ApplicationController(instance()) }
                }
            }
        }

        controller("/protected") { DIControllerImpl(instance()) }
    }
}