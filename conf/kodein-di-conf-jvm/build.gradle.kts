plugins {
    id("kodein-jvm")
}

dependencies {
    expectedBy(project(":conf:kodein-di-conf-common"))
    compile(project(":core:kodein-di-core-jvm"))
    testImplementation(project(":test-utils:test-utils-jvm"))
    testImplementation(project(":erased:kodein-di-erased-jvm"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Conf-JVM"
        description = "KODEIN Dependency Injection that can be configured / mutated for the JVM & Android"
        repo = "Kodein-DI"
    }
}
