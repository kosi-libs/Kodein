plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation("androidx.appcompat:appcompat:1.0.2")
}

kodeinUpload {
    name = "Kodein-DI-Framework-Android"
    description = "Kodein-DI extensions with AndroidX compatibility"
}