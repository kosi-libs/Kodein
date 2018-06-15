plugins {
    id("kodein-js")
}

dependencies {
    expectedBy(project(":core:kodein-di-core-common"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Core-JS"
        description = "KODEIN Dependency Injection Core for Javascript"
        repo = "Kodein-DI"
    }
}
