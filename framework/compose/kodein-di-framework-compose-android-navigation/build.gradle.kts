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
                api(projects.framework.compose.kodeinDiFrameworkComposeAndroid)
                implementation(libs.android.x.lifecycle.viewmodel.compose)
                implementation(libs.android.compose.navigation)
            }
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Android-Navigation"
    description = "Kodein extensions for Jetpack Compose Navigation scoped ViewModels"
}