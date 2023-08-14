plugins {
    kodein.library.android
}

dependencies {
    api(projects.kodeinDi)
}

android {
    namespace = "org.kodein.di.android"
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Standard Kodein classes & extensions for Android"
}