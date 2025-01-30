plugins {
    kodein.library.mpp
    alias(libs.plugins.kotlinx.atomicfu)
}

kotlin.kodein {
    all()

    common {
        mainDependencies {
            api(projects.kodeinDi)
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
    name = "Kodein-JxInject"
    description = "Kodein that can be use with JSR-330: Using reflexivity to auto-inject"
}
