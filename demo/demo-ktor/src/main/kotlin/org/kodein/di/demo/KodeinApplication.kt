package org.kodein.di.demo

import freemarker.cache.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import kodein.di.demo.*
import kodein.di.demo.coffee.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.*

//region App setup
@KtorExperimentalLocationsAPI
fun Application.main() {
    val application = this

    install(DefaultHeaders)
    install(Locations)
    install(CallLogging)
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(KodeinFeature) {
        import(thermosiphonModule)
        import(electricHeaterModule)

        bind<Application>() with instance(application)
        bind<CommonLogger>() with singleton { KtorLogger(instance()) }
        bind<Coffee>() with provider { Coffee(instance()) }

        // this is bound in the scope of an activity so any retrieval using the same activity will return the same Kettle instance
        bind<Kettle<*>>() with scoped(SessionScope).singleton { Kettle<Coffee>(instance(), instance(), instance(), provider()) }

        constant("author") with "Romain BOISSELLE"
    }

    val author: String by kodein().instance("author")
    logger.log("Setup Ktor application by $author")

    routeModule()
    logUserModule()
    homeModule()
    coffeeMakerModule()
}

val Application.logger get(): CommonLogger {
    val ktorLogger by kodein().instance<CommonLogger>()
    return ktorLogger
}

@Location("/") class Index
@Location("/home") class Home
@Location("/login") class Login(val username: String = "") {
    @Location("/clear") class ClearSession()
}
@Location("/kettle") class CoffeeMaker {
    @Location("/on") class On
    @Location("/off") class Off
    @Location("/brew") class Brew
}

@KtorExperimentalLocationsAPI
fun Application.routeModule() {
    routing {
        get<Index> {
            checkSessionOrRedirect() ?: return@get
            call.respondRedirect(locations.href(Home()))
        }
    }
}
//endregion

//region Session module
class UserSession(val username: String) : KtorSession {
    override fun getSessionId() = username
}

@KtorExperimentalLocationsAPI
fun Application.logUserModule() {
    install(Sessions) {
        cookie<UserSession>("SESSION_FEATURE_SESSION_ID", SessionStorageMemory()) {
            cookie.path = "/" // Specify cookie's path '/' so it can be used in the whole site
        }
    }

    routing {
        get<Login> {
            logger.log("Load login page")
            checkSessionOrRedirect {
                call.respond(FreeMarkerContent("login.ftl", mapOf("username" to "romain"), ""))
            }
        }
        post<Login> {
            val username = call.receive<Parameters>()["username"]
            logger.log("Login Attempt for [$username]")

            if (username == null) {
                logger.log("Login fail for [$username]")
                call.respondRedirect(locations.href(Login()))
            } else {
                logger.log("Login succeed for [$username]")
                call.sessions.getOrSet { UserSession(username) }
                call.respondRedirect(locations.href(Home()))
            }
        }
        post<Login.ClearSession> {
            call.sessions.clearSessionScope<UserSession>()
            call.respondRedirect(locations.href(Login()))
        }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>
        .checkSessionOrRedirect(action: suspend () -> Unit = { call.respondRedirect(locations.href(Login())) }): UserSession? {
    val session = call.sessions.get<UserSession>()

    if(session == null) {
        action()
    }

    return session
}
//endregion

//region Home module
@KtorExperimentalLocationsAPI
fun Application.homeModule() {
    routing {
        get<Home> {
            val session = checkSessionOrRedirect()?: return@get

            logger.log("load Hone page for the user ${session.username}")
            call.respond(FreeMarkerContent("home.ftl", mapOf("session" to session), ""))
        }
    }
}
//endregion


//region Coffee Maker module
@KtorExperimentalLocationsAPI
fun Application.coffeeMakerModule() {
    routing {
        post<CoffeeMaker.Brew> {
            val session = checkSessionOrRedirect()?: return@post

            val kettle: Kettle<*> by kodein().on(session).instance()
            logger.log("Brewing coffee for ${session.username} with $kettle scoped on $session")
            kettle.brew()

            call.respond(FreeMarkerContent("home.ftl", mapOf("session" to session, "coffee" to true), ""))
        }
    }
}
//endregion