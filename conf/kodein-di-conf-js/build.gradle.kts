plugins {
    id("kodein-js")
}

dependencies {
    expectedBy(project(":conf:kodein-di-conf-common"))
    compile(project(":core:kodein-di-core-js"))
    testImplementation(project(":test-utils:test-utils-js"))
    testImplementation(project(":erased:kodein-di-erased-js"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Conf-JS"
        description = "KODEIN Dependency Injection that can be configured / mutated for Javascript"
        repo = "Kodein-DI"
    }
}
