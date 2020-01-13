plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {

        common {
            main.dependencies {
                api(project(":kodein-di"))
            }

            test.dependencies {
                implementation(project(":test-utils"))
            }
        }

        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()

            test.dependencies {
                implementation("org.jetbrains.kotlin:kotlin-reflect")
            }
        }

        add(kodeinTargets.js.js)

        add(kodeinTargets.native.all)

    }
}

kodeinUpload {
    name = "Kodein-DI-Erased"
    description = "KODEIN Dependency Injection using erased types by default"
}
