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
            api(projects.framework.compose.kodeinDiFrameworkAndroidXCompose)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
            implementation(libs.android.compose.navigation)
        }

    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Android-Navigation"
    description = "Kodein extensions for AndroidX navigation scoped ViewModels using Jetpack Compose"
}