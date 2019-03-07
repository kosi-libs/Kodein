package org.kodein.di.demo

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import org.kodein.di.ktor.KodeinFeature

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(KodeinFeature) {
    }
}