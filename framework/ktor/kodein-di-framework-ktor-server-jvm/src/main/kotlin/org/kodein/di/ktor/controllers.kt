package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.di.*

abstract class KodeinController(val application: Application) : KodeinAware {
    override val kodein: Kodein by lazy { application.kodein }
    abstract fun Routing.installRoutes()
}