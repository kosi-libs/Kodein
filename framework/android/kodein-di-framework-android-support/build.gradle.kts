plugins {
    kodein.library.android
}

dependencies {
    api(projects.framework.android.kodeinDiFrameworkAndroidCore)

    implementation(libs.android.appcompat)
}

android {
    namespace = "org.kodein.di.android.support"
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein classes & extensions with 'android.support' compatibility"
}