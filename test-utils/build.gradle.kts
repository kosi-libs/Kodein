plugins {
    id("org.kodein.mpp")
}

kodein {
    kotlin {

        common.main.dependencies {
            api(libs.kotlin.test)
        }

        add(kodeinTargets.jvm.jvm) {
            main.dependencies {
                api(libs.kotlin.test.junit)
                api(libs.junit)
            }
        }

        add(kodeinTargets.js.ir.js)

        add(kodeinTargets.native.all)

    }
}
