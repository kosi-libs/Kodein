plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation("androidx.appcompat:appcompat:1.3.0")
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein extensions with AndroidX compatibility"
}