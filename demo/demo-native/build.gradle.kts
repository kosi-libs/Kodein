plugins {
    id("konan")
}

konanArtifacts {
    program("demo") {
        libraries {
            allLibrariesFrom(project(":erased:kodein-di-erased-native"))
        }
    }
}
