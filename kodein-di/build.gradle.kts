plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {

        common {
            main.dependencies {
                api("org.kodein.type:kaverit:2.0.1")
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
