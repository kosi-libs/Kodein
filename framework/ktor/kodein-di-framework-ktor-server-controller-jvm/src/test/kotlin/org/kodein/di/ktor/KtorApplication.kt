package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.features.*
import org.kodein.di.*
import org.kodein.di.generic.*

const val AUTHOR = "Romain Boisselle"
const val VERSION = "1.0.0"

fun Application.installKodein() = kodein {
    bind<ApplicationController>() with singleton { ApplicationController(instance()) }
    constant("author") with AUTHOR
    constant("version") with VERSION
}

fun Application.kodeinAbsControllerSuccess() {
    install(DefaultHeaders)
    installKodein()
    install(KodeinControllerFeature)
}

fun Application.kodeinAbsControllerFailure() {
    install(DefaultHeaders)
    install(KodeinControllerFeature)
    installKodein()
}

fun Application.kodeinControllerImplSuccess() {
    install(DefaultHeaders)
    installKodein()
    install(KodeinControllerFeature)
}

fun Application.kodeincontrollerImplFailure() {
    install(DefaultHeaders)
    install(KodeinControllerFeature)
    installKodein()
}