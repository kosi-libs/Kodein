plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {

        common {
            main.dependencies {
                api(libs.kosi.kaverit)
            }
            test.dependencies {
                implementation(project(":test-utils"))
            }
        }
        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }
        add(kodeinTargets.js.js)
        add(kodeinTargets.native.all)

    }
}

kodeinUpload {
    name = "Kodein"
    description = "Kodein Core"
}
