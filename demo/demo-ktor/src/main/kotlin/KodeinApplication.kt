
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import kotlinx.html.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.*
import java.util.*

//region APP starter
fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8000) {
        install(DefaultHeaders)
        install(CallLogging)

        kodein {
            bind() from scoped(SessionScope).singleton { Random() }
            bind() from scoped(CallScope).singleton { Random() }
        }

        sessionModule()
        requestModule()
    }.start(true)
}
//endregion

//region Scope demo
data class UserSession(val counter: Int) : KodeinSession {
    override fun getSessionId() = counter
}

private fun Application.sessionModule() {
    install(Sessions) {
        cookie<UserSession>("SESSION_FEATURE_SESSION_ID", SessionStorageMemory()) {
            cookie.path = "/" // Specify cookie's path '/' so it can be used in the whole site
        }
    }

    routing {

        val sessionRoute = "/session"
        val viewRoute = "/view"
        val incrementRoute = "/increment"
        val clearRoute = "/clear"
        val homeRoute = sessionRoute + viewRoute
        val info: ApplicationCall.() -> String =  { "[${request.httpMethod.value}] ${request.uri}" }

        get {
            call.respondRedirect(homeRoute)
        }

        route(sessionRoute) {
            get {
                call.respondRedirect(homeRoute)
            }

            get(viewRoute) {
                val session = call.sessions.get<UserSession>() ?: UserSession(0)

                val random by kodein().on(session).instance<Random>()
                application.log.info("${call.info()} / Session: $session / Kodein ${kodein().container} / Random instance: $random")

                call.respondHtml {
                    head {
                        title { +"Ktor: sessions" }
                    }
                    body {
                        p {
                            +"Hello from Ktor Sessions sample application"
                        }
                        p {
                            +"Counter: ${session.counter}"
                        }
                        nav {
                            ul {
                                li { a(sessionRoute + incrementRoute) { +"increment" } }
                                li { a(sessionRoute + viewRoute) { +"view" } }
                                li { a(sessionRoute + clearRoute) { +"clear" } }
                            }
                        }
                    }
                }
            }

            get(incrementRoute) {
                application.log.info("${call.info()} Increment session IN - ${call.sessions.get<UserSession>()}")
                val session = call.sessions.get<UserSession>() ?: UserSession(0)
                call.sessions.set(UserSession(session.counter + 1))
                application.log.info("${call.info()} Increment session OUT - ${call.sessions.get<UserSession>()}")
                call.respondRedirect(homeRoute)
            }

            get(clearRoute) {
                application.log.info("${call.info()} Clear session IN - ${call.sessions.get<UserSession>()}")
                call.sessions.clearSessionScope<UserSession>()
                application.log.info("${call.info()} Clear session OUT - ${call.sessions.get<UserSession>()}")
                call.respondRedirect(homeRoute)
            }
        }
    }
}

fun Application.requestModule() {
    routing {
        route("/request") {
            suspend fun logPhase(
                phase: String,
                applicationCall: ApplicationCall,
                proceed: suspend () -> Unit
            ) {
                val random by kodein().on(applicationCall).instance<Random>()
                log.info("Context $applicationCall / Kodein ${kodein().container} / $phase Random instance: $random")
                proceed()
            }

            intercept(ApplicationCallPipeline.Setup) {
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
                logPhase("[GET", context) {
                    call.respondText("Request processing...")
                }
            }
        }
    }
}
//endregion