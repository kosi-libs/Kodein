plugins {
    id("org.kodein.library.mpp-with-android")
    alias(libs.plugins.compose)
}

kodein {
    kotlin {
        common.main.dependencies {
            compileOnly(compose.runtime)
            api(project(":kodein-di"))
        }

        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }

        add(kodeinTargets.jvm.android) {
            main.dependencies {
                api(project(":framework:android:kodein-di-framework-android-x"))
                api(libs.android.x.lifecycle.viewmodel.compose)
            }
        }

        add(kodeinTargets.js.ir.js)

        // iosX32 not supported by jetbrains compose
        add(kodeinTargets.native.iosX64)
        add(kodeinTargets.native.iosArm64)
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}