plugins {
    id("org.kodein.library.mpp")
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

    wasm {
        target {
            browser {
                testTask(Action { enabled = false })
            }
        }
    }
}

kodeinUpload {
    name = "Kodein-JxInject"
    description = "Kodein that can be use with JSR-330: Using reflexivity to auto-inject"
}
