plugins {
    id("kodein-common")
}

dependencies {
    compile(project(":core:kodein-di-core-common"))
    testImplementation(project(":test-utils:test-utils-common"))
    testImplementation(project(":erased:kodein-di-erased-common"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Conf-Common"
        description = "KODEIN Dependency Injection that can be configured / mutated Commons"
        repo = "Kodein-DI"
    }
}
