plugins {
    id("kodein-common")
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Erased-Common"
        description = "KODEIN Dependency Injection using erased types by default Commons"
        repo = "Kodein-DI"
    }
}

setProperty("archivesBaseName", "Kodein_DI_Erased_Common")

dependencies {
    compile(project(":core:kodein-di-core-common"))
    testImplementation(project(":test-utils:test-utils-common"))
}
