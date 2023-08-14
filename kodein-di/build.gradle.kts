plugins {
    id("org.kodein.library.mpp")
}

kotlin.kodein {
    all()

    common {
        mainDependencies {
            api(libs.kosi.kaverit)
        }
        testDependencies {
            implementation(projects.testUtils)
        }
    }

    jvm {
        target.setCompileClasspath()
    }
}

kotlin.sourceSets {
    val jsBasedMain by getting
    getByName("wasmJsMain").dependsOn(jsBasedMain)
}

kodeinUpload {
    name = "Kodein"
    description = "Kodein Core"
}
