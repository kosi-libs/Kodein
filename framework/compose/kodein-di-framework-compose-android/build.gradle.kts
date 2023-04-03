plugins {
    id("org.kodein.library.mpp-with-android")
    alias(libs.plugins.compose)
}

kodein {
    kotlin {
        common.main.dependencies {
            compileOnly(compose.runtime)
            api(projects.framework.compose.kodeinDiFrameworkCompose)
        }

        add(kodeinTargets.jvm.android) {
            main.dependencies {
                api(projects.framework.android.kodeinDiFrameworkAndroidX)
                implementation(libs.android.x.lifecycle.viewmodel.compose)
            }
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Android"
    description = "Kodein extensions for Jetpack Compose ViewModels"
}