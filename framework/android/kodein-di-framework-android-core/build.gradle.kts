plugins {
    id("kodein-android")
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Framework-Android"
        description = "Kodein DI Kotlin classes & extensions for the Android platform"
        repo = "Kodein-DI"
    }
}

dependencies {
    api(project(":core:kodein-di-core-jvm"))

    testImplementation("junit:junit:4.12")
}
