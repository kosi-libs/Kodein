package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.features.*
import org.kodein.di.generic.*

const val AUTHOR = "Romain Boisselle"
const val VERSION = "1.0.0"

fun Application.main() {
    install(DefaultHeaders)
    install(KodeinControllerFeature) {
        bind<ApplicationController>() with singleton { ApplicationController(instance()) }

        constant("author") with AUTHOR
        constant("version") with VERSION
    }
}