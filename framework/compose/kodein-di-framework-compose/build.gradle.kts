plugins {
    id("org.jetbrains.compose") version "1.2.0"
    id("org.kodein.library.mpp-with-android")
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
                api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
            }
        }
        // JS not supported for Kotlin 1.7.20
        // (https://github.com/JetBrains/compose-jb/blob/master/VERSIONING.md#kotlin-compatibility)
        //        add(kodeinTargets.js.ir.js)

        // iosX32 not supported by jetbrains compose
        add(kodeinTargets.native.iosX64)
        add(kodeinTargets.native.iosArm64)
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}