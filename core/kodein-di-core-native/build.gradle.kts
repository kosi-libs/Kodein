plugins {
    id("kodein-native")
}

dependencies {
    expectedBy(project(":core:kodein-di-core-common"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Core-Native"
        description = "KODEIN Dependency Injection Core for Native Platforms"
        repo = "Kodein-DI"
    }
}
