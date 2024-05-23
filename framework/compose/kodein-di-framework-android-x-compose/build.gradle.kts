plugins {
    kodein.library.android
    alias(kodeinGlobals.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
}

dependencies {
    api(projects.framework.compose.kodeinDiFrameworkCompose)
    implementation(libs.android.x.compose.navigation)
    implementation(platform(libs.android.compose.bom))
    implementation(libs.android.compose.runtime)
}

android {
    namespace = "org.kodein.di.compose.android"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Android"
    description = "Kodein extensions for AndroidX ViewModels using Jetpack Compose"
}