plugins {
    id("kodein-js")
}

dependencies {
    expectedBy(project(":erased:kodein-di-erased-common"))
    compile(project(":core:kodein-di-core-js"))
    testImplementation(project(":test-utils:test-utils-js"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Erased-JS"
        description = "KODEIN Dependency Injection using erased types by default for Javascript (JS does NOT support genericity)"
        repo = "Kodein-DI"
    }
}
