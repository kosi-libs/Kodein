plugins {
    kodein.library.android
}

dependencies {
    api(projects.framework.android.kodeinDiFrameworkAndroidCore)

    implementation(libs.android.x.appcompat)
}

android {
    namespace = "org.kodein.di.android.x"
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein extensions with AndroidX compatibility"
}