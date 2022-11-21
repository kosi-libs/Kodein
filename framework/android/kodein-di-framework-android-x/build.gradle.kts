plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation(libs.android.x.appcompat)
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein extensions with AndroidX compatibility"
}