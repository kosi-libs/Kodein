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

kodeinUpload {
    name = "Kodein"
    description = "Kodein Core"
}
