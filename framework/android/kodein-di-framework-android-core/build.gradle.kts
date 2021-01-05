plugins {
    id("org.kodein.library.android")
}

dependencies {
    api(project(":kodein-di"))
}

kodeinUpload {
    name = "kodein-di-framework-android-core"
    description = "Kodein DI classes & extensions for Android"
}