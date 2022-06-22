plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation("com.android.support:appcompat-v7:28.0.0")
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein classes & extensions with 'android.support' compatibility"
}