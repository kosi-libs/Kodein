import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.*
import java.security.SecureRandom
import kotlin.random.Random
import kotlin.random.asKotlinRandom

fun Application.configureKodeinDI() {
    // Install Kodein-DI plugin
    di {
        // Bind dependencies
        bind<Random> { singleton { SecureRandom().asKotlinRandom() } }
    }

    routing {
        get("/di-example") {
            // Retrieve dependencies using closestDI()
            val random by closestDI().instance<Random>()
            call.respondText("Kodein-DI is working! Random number: ${random.nextInt(100)}")
        }
    }
}
