plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(projects.kodeinDi)
}

kodeinUpload {
    name = "Kodein-Framework-Android"
    description = "Standard Kodein classes & extensions for Android"
}