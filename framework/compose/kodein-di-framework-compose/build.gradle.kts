plugins {
    id("org.kodein.library.mpp-with-android")
    alias(libs.plugins.compose)
}

kodein {
    kotlin {
        common.main.dependencies {
            compileOnly(compose.runtime)
            api(projects.kodeinDi)
        }

        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }

        add(kodeinTargets.jvm.android) {
            main.dependencies {
                api(projects.framework.android.kodeinDiFrameworkAndroidX)
                api(libs.android.x.lifecycle.viewmodel.compose)
            }
        }

        add(kodeinTargets.js.js)

        // iosX32 not supported by jetbrains compose
        add(kodeinTargets.native.iosX64)
        add(kodeinTargets.native.iosArm64)
        add(kodeinTargets.native.iosSimulatorArm64)

        add(kodeinTargets.native.macosX64)
        add(kodeinTargets.native.macosArm64)
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}