plugins {
    id("org.jetbrains.compose") version "0.3.2"
    id("org.kodein.library.mpp-with-android")
}

kodein {
    kotlin {
        common.main.dependencies {
            compileOnly(compose.runtime)
            compileOnly(compose.foundation)
            api(project(":kodein-di"))
        }

        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }

        add(kodeinTargets.jvm.android) {
            main.dependencies {
                api(project(":framework:android:kodein-di-framework-android-x"))
            }
        }
    }
}

kodeinUpload {
    name = "Kodein-DI-Framework-Compose"
    description = "Kodein-DI extensions for Jetpack / JetBrains Compose"
}