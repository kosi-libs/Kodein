plugins {
    id("kodein-native")
}

dependencies {
    expectedBy(project(":erased:kodein-di-erased-common"))
    implementation(project(":core:kodein-di-core-native"))
    testImplementation(project(":test-utils:test-utils-native"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Erased-Native"
        description = "KODEIN Dependency Injection using erased types by default for Native Platforms (Native Platforms do NOT support genericity)"
        repo = "Kodein-DI"
    }
}
