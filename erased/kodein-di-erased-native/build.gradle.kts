plugins {
    id("kodein-native")
    `maven-publish`
}

//kodeinPublication {
//    name = "Kodein-DI-Erased-Native"
//    description = "KODEIN Dependency Injection using erased types by default for Native Platforms (Native Platforms do NOT support genericity)"
//    repo = "Kodein-DI"
//}

konanArtifacts {
    library(mapOf("targets" to kodeinNative.allTargets), project.name) {
        enableMultiplatform(true)
        libraries {
            allLibrariesFrom(project(":core:kodein-di-core-native"))
        }

        program("tests") {
            commonSourceSets("test")
            libraries {
                allLibrariesFrom(project(":test-utils:test-utils-native"))
                artifact(project.name)
            }
            extraOpts("-tr")
        }
    }
}

dependencies {
    expectedBy(project(":erased:kodein-di-erased-common"))
}

task("test") {
    dependsOn("runTests")
}

//ext {
//    libName = "kodein-di-erased-${project.version}"
//    pom_name = "Kodein-DI-Erased-Native"
//    pom_description = "KODEIN Dependency Injection using erased types by default for Native Platforms (Native Platforms do NOT support genericity)"
//}
//apply from: "../../gradle/klib-universal.gradle"
//apply from: "../../gradle/klib-upload.gradle"
