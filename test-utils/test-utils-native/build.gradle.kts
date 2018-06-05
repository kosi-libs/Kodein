plugins {
    id("konan")
}

konanArtifacts {
    library("test-utils") {
        enableMultiplatform(true)
    }
}

dependencies {
    expectedBy(project(":test-utils:test-utils-common"))
}
