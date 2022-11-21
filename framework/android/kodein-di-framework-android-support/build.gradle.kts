plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation(libs.android.appcompat)
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein classes & extensions with 'android.support' compatibility"
}