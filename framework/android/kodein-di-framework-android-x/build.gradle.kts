plugins {
    kodein.library.android
}

dependencies {
    api(projects.framework.android.kodeinDiFrameworkAndroidCore)

    implementation(libs.android.x.appcompat)
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein extensions with AndroidX compatibility"
}