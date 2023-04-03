plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(projects.framework.android.kodeinDiFrameworkAndroidCore)

    implementation(libs.android.appcompat)
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Kodein classes & extensions with 'android.support' compatibility"
}