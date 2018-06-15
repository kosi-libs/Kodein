plugins {
    id("kodein-jvm")
    id("kodein-versions")
}

dependencies {
    expectedBy(project(":erased:kodein-di-erased-common"))
    compile(project(":core:kodein-di-core-jvm"))
    testImplementation(project(":test-utils:test-utils-jvm"))
    testImplementation("com.google.inject:guice:4.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${kodeinVersions.kotlin}")
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Erased-JVM"
        description = "KODEIN Dependency Injection using erased types by default for the JVM & Android (optimized, but error-prone)"
        repo = "Kodein-DI"
    }
}
