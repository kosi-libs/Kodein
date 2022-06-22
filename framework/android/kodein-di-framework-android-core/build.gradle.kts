plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":kodein-di"))
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Standard Kodein classes & extensions for Android"
}