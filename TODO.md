Kodein DI todo list
===================

CORE
----

#### Suspending factories

Factories (such as `factory {}`, `provider {}`, `singleton {}`, etc.) should take suspendable functions as parameter.

This would enable code such as:

```kotlin
Kodein {
    bind<UserList>() with singleton { api.userList().await() }
}
```

This is a **major change**, as retrieval functions must also become suspending functions.
Therefore, we need to provide a way for blocking retrieval to be as easy as `kodein.direct`.


FRAMEWORKS
----------

#### TornadoFX

[TornadoFX](https://github.com/edvin/tornadofx) is a JavaFX framework for Kotlin.

It uses some kind of IoC for binding views, components and controllers.
These  bindings could be integrated to Kodein, especially through the `externalSource` feature.

Further more, Kodein could provide special scopes for TornadoFX (controller, component, etc.).

#### KTor

[Ktor](https://ktor.io/) is a framework for building asynchronous servers and clients.

Kotlin could provide special scopes for KTor servers (session, request, route, etc.).

#### Cocoa-Touch

Cocoa Touch is the Core Framework on iOS.

Kotlin should provide the same kind of services on iOS it provides for Android.
Namely: a module to retrieve core services and scopes for MVC controllers (ViewController on iOS).

Furthermore, Kodein is currently incompatible with Obj-C & Swift and requires a facade.
This must be addressed.
