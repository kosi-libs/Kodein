plugins {
    id("kodein-native")
}

konanArtifacts {
    library(mapOf("targets" to kodeinNative.allTargets), project.name) {
        enableMultiplatform(true)
        libraries {
            allLibrariesFrom(project(":core:kodein-di-core-native"))
        }

        program("test") {
            commonSourceSets("test")
            libraries {
                allLibrariesFrom(project(":test-utils:test-utils-native"))
                allLibrariesFrom(project(":erased:kodein-di-erased-native"))
                artifact(project.name)
            }
            extraOpts("-tr")
        }
    }
}

dependencies {
    expectedBy(project(":conf:kodein-di-conf-common"))
}

task("test") {
    dependsOn("runTest")
}

kodeinPublication {
    name = "Kodein-DI-Conf-Native"
    description = "KODEIN Dependency Injection that can be configured / mutated for Native Platforms"
    repo = "Kodein-DI"
}
