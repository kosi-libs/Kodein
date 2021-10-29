package org.kodein.di.ktor

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.kodein.di.*
import java.util.*

// Test Ktor Application
fun Application.main() {
    install(DefaultHeaders)

    di {
        bind { scoped(SessionScope).singleton { Random() } }
        bind { scoped(CallScope).singleton { Random() } }

        constant("author") with AUTHOR.lowercase()
    }

    sessionModule()
    requestModule()
    closestModule()
    subDIModule()
}

const val ROUTE_SESSION = "/session"
const val ROUTE_INC = "/increment"
const val ROUTE_CLEAR = "/clear"
const val ROUTE_REQUEST = "/request"
const val ROUTE_CLOSEST = "/closest"
const val ROUTE_SUBKODEIN = "/sub"
const val ROUTE_SUB_LOWER = "/lower"
const val ROUTE_SUB_UPPER = "/upper"

const val SESSION_FEATURE_SESSION_ID = "SESSION_FEATURE_SESSION_ID"
const val NO_SESSION = "NO_SESSION"

const val AUTHOR = "romain boisselle"

data class MockSession(val counter: Int = 0) : KodeinDISession {
    override fun getSessionId() = counter
}

data class RandomDto(val randomInstances: MutableList<Pair<String, String>> = mutableListOf())

private val randomDto = RandomDto()

private fun Application.sessionModule() {
    install(Sessions) {
        cookie<MockSession>("SESSION_FEATURE_SESSION_ID", SessionStorageMemory()) {
            cookie.path = "/" // Specify cookie's path '/' so it can be used in the whole site
        }
    }

    routing {

        val info: ApplicationCall.() -> String = { "[${request.httpMethod.value}] ${request.uri}" }

        route(ROUTE_SESSION) {
            get {
                application.log.info("${closestDI()}")
                val session = call.sessions.get<MockSession>() ?: MockSession(0)
                val random by closestDI().on(session).instance<Random>()

                application.log.info("${call.info()} / Session: $session / DI ${closestDI().container} / Random instance: $random")

                call.respondText("$random")
            }

            get(ROUTE_INC) {
                application.log.info("${call.info()} Increment session IN - ${call.sessions.get<MockSession>()}")
                val session = call.sessions.get<MockSession>() ?: MockSession(0)
                call.sessions.set(MockSession(session.counter + 1))
                application.log.info("${call.info()} Increment session OUT - ${call.sessions.get<MockSession>()}")

                call.respondText("${call.sessions.get<MockSession>()}")
            }

            get(ROUTE_CLEAR) {
                application.log.info("${call.info()} Clear session IN - ${call.sessions.get<MockSession>()}")
                call.sessions.clearSessionScope<MockSession>()
                application.log.info("${call.info()} Clear session OUT - ${call.sessions.get<MockSession>()}")

                call.respondText("${call.sessions.get<MockSession>()}")
            }
        }

    }
}

fun Application.requestModule() {
    routing {
        route(ROUTE_REQUEST) {
            suspend fun logPhase(
                    phase: String,
                    applicationCall: ApplicationCall,
                    proceed: suspend () -> Unit
            ) {
                val random by closestDI().on(applicationCall).instance<Random>()
                randomDto.randomInstances.add(phase to "$random")
                log.info("Context $applicationCall / DI ${closestDI().container} / $phase Random instance: $random")
                proceed()
            }

            intercept(ApplicationCallPipeline.Setup) {
                randomDto.randomInstances.clear()
                logPhase("[Setup]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Monitoring) {
                logPhase("[Monitoring]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Plugins) {
                logPhase("[Features]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Call) {
                logPhase("[Call]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Fallback) {
                logPhase("[Fallback]", context) { proceed() }
            }

            get {
                val random by closestDI().on(context).instance<Random>()
                application.log.info("DI ${closestDI().container} / Random instance: $random")
                logPhase("[GET]", context) {
                    call.respondText(randomDto.randomInstances.joinToString { "${it.first}=${it.second}" })
                }
            }
        }
    }
}


fun Application.closestModule() {
    val kodeinInstances = mutableListOf<DI>()
    routing {
        kodeinInstances.add(closestDI().baseDI)
        route(ROUTE_CLOSEST) {
            kodeinInstances.add(closestDI().baseDI)
            get {
                kodeinInstances.add(closestDI().baseDI)
                call.respondText(kodeinInstances.joinToString())
            }
        }
    }
}

fun Application.subDIModule() {
    val kodeinInstances = mutableListOf<DI>()
    routing {
        route(ROUTE_SUBKODEIN) {
            kodeinInstances.add(closestDI().baseDI)

            route(ROUTE_SUB_LOWER) {
                get {
                    val author: String by closestDI().instance("author")
                    call.respondText(author)
                }
            }

            route(ROUTE_SUB_UPPER) {
                subDI(allowSilentOverride = true, init = {
                    constant("author") with AUTHOR.uppercase()
                })

                get {
                    val author: String by closestDI().instance("author")
                    call.respondText(author)
                }
                post {
                    kodeinInstances.add(closestDI().baseDI)
                    call.respondText(kodeinInstances.joinToString())
                }
            }
        }
    }
}
//endregion