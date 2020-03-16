package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.kodein.di.*
import java.util.*

// Test Ktor Application
fun Application.main() {
    install(DefaultHeaders)

    di {
        bind() from scoped(SessionScope).singleton { Random() }
        bind() from scoped(CallScope).singleton { Random() }

        constant("author") with AUTHOR.toLowerCase()
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
                application.log.info("${di()}")
                val session = call.sessions.get<MockSession>() ?: MockSession(0)
                val random by di().on(session).instance<Random>()

                application.log.info("${call.info()} / Session: $session / DI ${di().container} / Random instance: $random")

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
                val random by di().on(applicationCall).instance<Random>()
                randomDto.randomInstances.add(phase to "$random")
                log.info("Context $applicationCall / DI ${di().container} / $phase Random instance: $random")
                proceed()
            }

            intercept(ApplicationCallPipeline.Setup) {
                randomDto.randomInstances.clear()
                logPhase("[Setup]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Monitoring) {
                logPhase("[Monitoring]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Features) {
                logPhase("[Features]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Call) {
                logPhase("[Call]", context) { proceed() }
            }
            intercept(ApplicationCallPipeline.Fallback) {
                logPhase("[Fallback]", context) { proceed() }
            }

            get {
                val random by di().on(context).instance<Random>()
                application.log.info("DI ${di().container} / Random instance: $random")
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
        kodeinInstances.add(di().baseDI)
        route(ROUTE_CLOSEST) {
            kodeinInstances.add(di().baseDI)
            get {
                kodeinInstances.add(di().baseDI)
                call.respondText(kodeinInstances.joinToString())
            }
        }
    }
}

fun Application.subDIModule() {
    val kodeinInstances = mutableListOf<DI>()
    routing {
        route(ROUTE_SUBKODEIN) {
            kodeinInstances.add(di().baseDI)

            route(ROUTE_SUB_LOWER) {
                get {
                    val author: String by di().instance("author")
                    call.respondText(author)
                }
            }

            route(ROUTE_SUB_UPPER) {
                subDI(allowSilentOverride = true, init = {
                    constant("author") with AUTHOR.toUpperCase()
                })

                get {
                    val author: String by di().instance("author")
                    call.respondText(author)
                }
                post {
                    kodeinInstances.add(di().baseDI)
                    call.respondText(kodeinInstances.joinToString())
                }
            }
        }
    }
}
//endregion