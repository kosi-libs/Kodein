plugins {
    id("kodein-native")
    `maven-publish`
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

//kodeinPublication {
//    name = "Kodein-DI-Conf-Native"
//    description = "KODEIN Dependency Injection that can be configured / mutated for Native Platforms"
//    repo = "Kodein-DI"
//}
//
//
////apply plugin: 'konan'
////
////konanArtifacts {
////    library("kodein-di-conf-${project.version}", targets: konanTargets) {
////        enableMultiplatform true
////
////        libraries {
////            allLibrariesFrom project(':core:kodein-di-core-native')
////        }
////    }
////
////    program("tests") {
////        commonSourceSet 'test'
////        libraries {
////            allLibrariesFrom project(':test-utils:test-utils-native')
////            allLibrariesFrom project(':erased:kodein-di-erased-native')
////            artifact "kodein-di-conf-${project.version}"
////        }
////        extraOpts '-tr'
////    }
////}
////
////dependencies {
////    expectedBy project(":conf:kodein-di-conf-common")
////}
////
////task test(dependsOn: run)
////
////ext {
////    libName = "kodein-di-conf-${project.version}"
////    pom_name = "Kodein-DI-Conf-Native"
////    pom_description = "KODEIN Dependency Injection that can be configured / mutated for Native Platforms"
////}
////apply from: "../../gradle/klib-universal.gradle"
////apply from: "../../gradle/klib-upload.gradle"
