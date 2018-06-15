plugins {
    id("kodein-jvm")
}

dependencies {
    expectedBy(project(":core:kodein-di-core-common"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Core-JVM"
        description = "KODEIN Dependency Injection Core for the JVM & Android"
        repo = "Kodein-DI"
    }
}
