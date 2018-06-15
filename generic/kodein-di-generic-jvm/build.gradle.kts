plugins {
    id("kodein-jvm")
    id("kodein-versions")
}

dependencies {
    compile(project(":core:kodein-di-core-jvm"))
    testImplementation("com.google.inject:guice:4.1.0")
    testImplementation(project(":test-utils:test-utils-jvm"))
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${kodeinVersions.kotlin}")
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Generic-JVM"
        description = "KODEIN Dependency Injection compatible with generic types for the JVM & Android"
        repo = "Kodein-DI"
    }
}
