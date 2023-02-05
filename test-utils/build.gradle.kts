plugins {
    id("org.kodein.mpp")
}

kodein {
    kotlin {

        common.main.dependencies {
            api(kodeinGlobals.kotlin.test)
        }

        add(kodeinTargets.jvm.jvm) {
            main.dependencies {
                api(kodeinGlobals.kotlin.test.junit)
            }
        }

        add(kodeinTargets.js.js)

        add(kodeinTargets.native.all)

    }
}
