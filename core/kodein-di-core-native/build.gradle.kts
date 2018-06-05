plugins {
    id("kodein-native")
    `maven-publish`
//    id("com.jfrog.bintray")
}

konanArtifacts {
    library(mapOf("targets" to kodeinNative.allTargets), name) {
        enableMultiplatform(true)
    }
}

dependencies {
    expectedBy(project(":core:kodein-di-core-common"))
}

//kodeinPublication {
//    name = "Kodein-DI-Core-Native"
//    description = "KODEIN Dependency Injection Core for Native Platforms"
//    repo = "Kodein-DI"
//}
