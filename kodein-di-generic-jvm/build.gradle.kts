plugins {
    id("org.kodein.library.jvm")
}

dependencies {
    api(project(":kodein-di-core"))

    testImplementation("com.google.inject:guice:4.1.0")
    testImplementation(project(":test-utils"))
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
}

kodeinUpload {
    name = "Kodein-DI-Generic"
    description = "KODEIN Dependency Injection compatible with generic types"
}
