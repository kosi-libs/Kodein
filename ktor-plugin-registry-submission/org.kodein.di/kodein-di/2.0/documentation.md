Kodein-DI is a straightforward and yet very useful dependency retrieval container for Kotlin. It is effortless to use and configure.

More information on Kodein-DI can be [found here](https://kosi-libs.org/kodein/).

## Usage

To start using Kodein-DI in Ktor, install the DI plugin like the following:

```kotlin
import io.ktor.server.application.*
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di

fun Application.module() {
    // Install Kodein-DI
    // The Application instance is automatically bound
    di {
        // Add your bindings here
        bindSingleton<MyService> { MyServiceImpl() }
    }
}
```

## Retrieving Dependencies

Kodein-DI provides convenient property delegation for dependency retrieval:

```kotlin
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.*
import org.kodein.di.ktor.closestDI

fun Application.module() {
    di {
        bindSingleton<MyService> { MyServiceImpl() }
    }
    
    routing {
        get("/") {
            val myService: MyService by closestDI().instance()
            call.respondText(myService.getMessage())
        }
    }
}
```

## Scopes

Kodein-DI for Ktor provides several scope options:

### Session Scope

Bind dependencies to the session lifetime:

```kotlin
import org.kodein.di.ktor.SessionScope

di {
    bind<UserData>() with scoped(SessionScope).singleton { UserData() }
}
```

### Call Scope

Bind dependencies to a single request/call:

```kotlin
import org.kodein.di.ktor.CallScope

di {
    bind<RequestContext>() with scoped(CallScope).singleton { RequestContext() }
}
```

## Accessing the DI Container

You can access the DI container from various Ktor components:

```kotlin
import org.kodein.di.ktor.closestDI

// From Application
val di = application.closestDI()

// From ApplicationCall
val di = call.closestDI()

// From Route
val di = route.closestDI()
```

## Sub-DI Containers

Create sub-DI containers for modularization:

```kotlin
import org.kodein.di.ktor.subDI

routing {
    route("/api") {
        subDI {
            // Additional bindings specific to /api routes
            bind<ApiService>() with singleton { ApiServiceImpl() }
        }
        
        get("/endpoint") {
            val apiService: ApiService by closestDI().instance()
            // ...
        }
    }
}
```
