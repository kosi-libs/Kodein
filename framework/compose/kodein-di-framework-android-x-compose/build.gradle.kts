plugins {
    kodein.library.mppWithAndroid
    alias(libs.plugins.compose)
}

kotlin.kodein {
    common.mainDependencies {
        compileOnly(kotlin.compose.runtime)
        api(projects.framework.compose.kodeinDiFrameworkCompose)
    }

    android {
        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
            implementation(libs.android.compose.navigation)
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Android"
    description = "Kodein extensions for AndroidX ViewModels using Jetpack Compose"
}