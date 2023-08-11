plugins {
    kodein.library.android
}

dependencies {
    api(projects.framework.compose.kodeinDiFrameworkCompose)
    implementation(libs.android.compose.navigation)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
}

android {
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