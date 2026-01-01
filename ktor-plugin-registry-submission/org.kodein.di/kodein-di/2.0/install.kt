import io.ktor.server.application.*
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di

public fun Application.install() {
    di {
        bindSingleton { this@install }
        // Add your dependency bindings here
    }
}
