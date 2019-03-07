package org.kodein.di.demo

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
}

@Location("/")
class index()

@Location("/") class Index
@Location("/home") class Home
@Location("/login") class Login(val username: String = "") {
    @Location("/clear") class ClearSession()
}

@KtorExperimentalLocationsAPI
fun Application.routeModule() {
    routing {
        get<Index> {
            val session = call.sessions.get<UserSession>()
            if (session == null)
                call.respondRedirect(locations.href(Login()))
            else
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
        get<login> {
            call.respondText { "LOGIN" }
        }
    }
}
//endregion

//region Coffee Maker
@KtorExperimentalLocationsAPI
fun Application.coffeeMaker() {
    routing {
        get<home> {
            call.respondText { "HOME" }
        }
    }
}
//endregion
