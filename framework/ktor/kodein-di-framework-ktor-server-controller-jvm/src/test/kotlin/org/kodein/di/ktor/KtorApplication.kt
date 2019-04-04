package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.features.*
import org.kodein.di.generic.*

const val AUTHOR = "Romain Boisselle"
const val VERSION = "1.0.0"

fun Application.success() {
    install(DefaultHeaders)

    kodein {
        bind<ApplicationController>() with singleton { ApplicationController(instance()) }

        constant("author") with AUTHOR
        constant("version") with VERSION
    }
    install(KodeinControllerFeature)
}

fun Application.failure() {
    install(DefaultHeaders)

    install(KodeinControllerFeature)
    kodein {
        bind<ApplicationController>() with singleton { ApplicationController(instance()) }

        constant("author") with AUTHOR
        constant("version") with VERSION
    }
}