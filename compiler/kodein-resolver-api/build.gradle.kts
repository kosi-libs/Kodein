plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        common.main.dependencies {
            api(project(":kodein-di"))
        }
        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }
        add(kodeinTargets.native.all)
        add(kodeinTargets.js.ir.js)
    }
}

kodeinUpload {
    name = "kodein-resolver-api"
    description = "Kodein Resolver API"
}
