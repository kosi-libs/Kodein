plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        common.main.dependencies {
            api(projects.kodeinDi)
        }
        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }
        add(kodeinTargets.native.all)
        add(kodeinTargets.js.js)
    }
}

kodeinUpload {
    name = "kodein-resolver-api"
    description = "Kodein Resolver API"
}
