plugins {
    id("org.kodein.library.jvm")
}

kodeinLib {
    dependencies {
        api(project(":kodein-di") target "jvm")

        testImplementation("com.google.inject:guice:4.1.0")
        testImplementation(project(":test-utils"))
        testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    }
}

kodeinUpload {
    name = "Kodein-DI-Generic"
    description = "KODEIN Dependency Injection compatible with generic types"
}
