import io.ktor.server.application.*
import org.kodein.di.ktor.di

public fun Application.install() {
    // The Application instance is automatically bound
    di {
        // Add your dependency bindings here
    }
}
