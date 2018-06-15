plugins {
    id("kodein-native")
}

konanArtifacts {
    library(mapOf("targets" to kodeinNative.allTargets), project.name) {
        enableMultiplatform(true)
        libraries {
            allLibrariesFrom(project(":core:kodein-di-core-native"))
        }
    }
}

konanTests {
    testProgram(project.name) {
        libraries {
            allLibrariesFrom(project(":test-utils:test-utils-native"))
        }
    }
}

dependencies {
    expectedBy(project(":erased:kodein-di-erased-common"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Erased-Native"
        description = "KODEIN Dependency Injection using erased types by default for Native Platforms (Native Platforms do NOT support genericity)"
        repo = "Kodein-DI"
    }
}
