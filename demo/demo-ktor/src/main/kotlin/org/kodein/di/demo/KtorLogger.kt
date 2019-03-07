package org.kodein.di.demo

import io.ktor.application.*
import kodein.di.demo.*

class KtorLogger(private val application: Application) : CommonLogger {
    override fun log(msg: String) {
        application.log.info(msg)
    }
}
