plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":kodein-di"))
}

kodeinUpload {
    name = "Kodein-DI-Framework-Android"
    description = "Standard Kodein DI classes & extensions for Android"
}