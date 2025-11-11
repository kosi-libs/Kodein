# Kodein-DI for Ktor

Kodein-DI is a straightforward and powerful dependency injection framework for Kotlin Multiplatform. This plugin provides seamless integration with Ktor applications, making dependency management painless and idiomatic.

## Features

- **DIPlugin**: Ktor plugin for global DI container management
- **closestDI() Pattern**: Retrieve DI container from anywhere in your Ktor application hierarchy
- **Session-Scoped Dependencies**: Bind dependencies to user sessions
- **Call-Scoped Dependencies**: Bind dependencies to individual requests
- **Sub-DI Support**: Extend DI containers for specific routes
- **DI Controllers**: MVC-style architecture with built-in dependency injection
- **Multiplatform**: Works on all Kotlin/JVM targets

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:7.30.0")
}
```

## Basic Usage

### Setting up the DI Plugin

Install the DI plugin in your Ktor application:

```kotlin
fun main() {
    embeddedServer(Netty, port = 8080) {
        di {
            bind<Random> { singleton { SecureRandom() } }
            bind<DataSource> { singleton { DatabaseConnection() } }
        }

        routing {
            get("/") {
                val random by closestDI().instance<Random>()
                call.respondText("Random number: ${random.nextInt()}")
            }
        }
    }.start(wait = true)
}
```

### Retrieving Dependencies

Use the `closestDI()` function to access the DI container from anywhere:

```kotlin
routing {
    get("/users") {
        val dataSource: DataSource by closestDI().instance()
        val users = dataSource.getAllUsers()
        call.respond(users)
    }
}
```

## Advanced Features

### Session-Scoped Dependencies

Bind dependencies to user sessions for per-user state:

```kotlin
// 1. Define your session class implementing KodeinDISession
data class UserSession(val userId: String) : KodeinDISession {
    override fun getSessionId() = userId
}

// 2. Configure DI with session scope
fun Application.module() {
    install(Sessions) {
        cookie<UserSession>("SESSION_ID")
    }

    di {
        bind<ShoppingCart> { scoped(SessionScope).singleton { ShoppingCart() } }
    }

    routing {
        get("/cart") {
            val session = call.sessions.get<UserSession>() ?: error("Not logged in")
            val cart: ShoppingCart by closestDI().on(session).instance()
            call.respond(cart.items)
        }

        post("/cart/clear") {
            call.sessions.clearSessionScope<UserSession>()
            call.respondText("Cart cleared")
        }
    }
}
```

### Call-Scoped Dependencies

Bind dependencies to individual requests:

```kotlin
di {
    bind<RequestId> { scoped(CallScope).singleton { RequestId(UUID.randomUUID()) } }
}

routing {
    get("/api") {
        val requestId: RequestId by closestDI().on(call).instance()
        // Same RequestId instance throughout this request
        call.respond(mapOf("requestId" to requestId.value))
    }
}
```

### Route-Specific Dependencies with Sub-DI

Create route-specific DI containers:

```kotlin
fun Application.module() {
    di {
        bind<Database> { singleton { MainDatabase() } }
    }

    routing {
        route("/admin") {
            subDI {
                bind<AdminService> { singleton { AdminService(instance()) } }
            }

            get {
                val service: AdminService by closestDI().instance()
                // AdminService only available within /admin routes
                call.respond(service.getAdminData())
            }
        }
    }
}
```

### MVC-Style Controllers

Create controllers with automatic dependency injection:

```kotlin
// Define a controller
class UserController(application: Application) : AbstractDIController(application) {
    private val userRepository: UserRepository by instance()

    override fun Route.installRoutes() {
        get("/users") {
            call.respond(userRepository.getAllUsers())
        }

        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: error("Invalid ID")
            call.respond(userRepository.getUser(id))
        }

        post("/users") {
            val user = call.receive<User>()
            userRepository.save(user)
            call.respond(HttpStatusCode.Created)
        }
    }
}

// Install the controller
fun Application.module() {
    di {
        bind<UserRepository> { singleton { UserRepositoryImpl(instance()) } }
    }

    routing {
        controller("/api") { UserController(instance()) }
    }
}
```

## Configuration Options

### Binding Types

Kodein-DI supports various binding types:

```kotlin
di {
    // Singleton: Created once and reused
    bind<Config> { singleton { Config.load() } }

    // Provider: New instance every time
    bind<Random> { provider { Random() } }

    // Factory: New instance with argument
    bind<UserService> { factory { userId: String -> UserService(userId) } }

    // Multiton: Cached instance per unique argument
    bind<DatabaseConnection> { multiton { dbName: String ->
        DatabaseConnection(dbName)
    }}

    // Instance: Bind existing object
    bind<Clock> { instance(Clock.System) }
}
```

### Tags for Multiple Bindings

Use tags to distinguish between multiple bindings of the same type:

```kotlin
di {
    bind<DataSource>(tag = "local") { singleton { LocalDatabase() } }
    bind<DataSource>(tag = "remote") { singleton { RemoteDatabase() } }
}

routing {
    get("/local-data") {
        val db: DataSource by closestDI().instance(tag = "local")
        call.respond(db.getData())
    }
}
```

## Best Practices

1. **Bind interfaces, not implementations**: Makes testing and swapping implementations easier
2. **Use appropriate scopes**: Session for user state, Call for request state, Singleton for shared resources
3. **Organize with modules**: Split DI configuration across multiple modules for better organization
4. **Leverage controllers**: For complex applications, use controllers to organize routes and dependencies

## Resources

- [Full Documentation](https://kosi-libs.org/kodein/)
- [GitHub Repository](https://github.com/kosi-libs/Kodein)
- [Sample Projects](https://github.com/Kodein-Framework/Kodein-Samples)
- [Slack Channel](https://kotlinlang.slack.com/messages/kodein/)

## License

Kodein-DI is published under the [MIT License](https://github.com/kosi-libs/Kodein/blob/master/LICENSE.txt).
