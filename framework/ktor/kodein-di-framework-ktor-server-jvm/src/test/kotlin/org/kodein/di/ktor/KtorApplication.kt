package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.kodein.di.*
import org.kodein.di.generic.*
import java.security.*
import java.util.*

// Test Ktor Application
fun Application.main() {
    install(DefaultHeaders)
    install(KodeinFeature) {
        bind<Random>() with scoped(SessionScope).singleton { SecureRandom() }
        bind<Random>() with scoped(CallScope).singleton { SecureRandom() }
    }

    sessionModule()
    requestModule()
    closestModule()
    subKodeinModule()
}

const val ROUTE_SESSION = "/session"
const val ROUTE_INC = "/increment"
const val ROUTE_CLEAR = "/clear"
const val ROUTE_REQUEST = "/request"
const val ROUTE_CLOSEST = "/closest"
const val ROUTE_SUBKODEIN = "/sub"

const val SESSION_FEATURE_SESSION_ID = "SESSION_FEATURE_SESSION_ID"
const val NO_SESSION = "NO_SESSION"

data class MockSession(val counter: Int = 0) : KodeinSession {
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
                val session = call.sessions.get<MockSession>() ?: MockSession(0)
                val random by kodein().on(session).instance<Random>()

            application.log.info("${call.info()} / Session: $session / Kodein ${kodein().container} / Random instance: $random")

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
                val random by kodein().on(applicationCall).instance<Random>()
                randomDto.randomInstances.add(phase to "$random")
                log.info("Context $applicationCall / Kodein ${kodein().container} / $phase Random instance: $random")
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
                val random by kodein().on(context).instance<Random>()
                application.log.info("Kodein ${kodein().container} / Random instance: $random")
                logPhase("[GET]", context) {
                    call.respondText(randomDto.randomInstances.joinToString { "${it.first}=${it.second}" })
                }
            }
        }
    }
}


fun Application.closestModule() {
    val kodeinInstances = mutableListOf<Kodein>()
    routing {
        kodeinInstances.add(kodein())
        route(ROUTE_CLOSEST) {
            kodeinInstances.add(kodein())
            get {
                kodeinInstances.add(kodein())
                call.respondText(kodeinInstances.joinToString())
            }
        }
    }
}

fun Application.subKodeinModule() {
    val kodeinInstances = mutableListOf<Kodein>()
    routing {
        kodeinInstances.add(kodein())
        route(ROUTE_SUBKODEIN) {
            kodeinInstances.add(kodein())
            get {
                kodeinInstances.add(kodein())
                kodeinInstances.add(subKodein {  })
                call.respondText(kodeinInstances.joinToString())
            }
        }
    }
}
//endregion