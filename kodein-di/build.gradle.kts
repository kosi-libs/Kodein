plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {

        common {
            main.dependencies {
                api("org.kodein.type:kodein-type:1.7.1")
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
    name = "Kodein-DI"
    description = "KODEIN Dependency Injection Core"
}
