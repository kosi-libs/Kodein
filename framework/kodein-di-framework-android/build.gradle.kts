plugins {
    id("kodein-android")
}

kodeinPublication {
    name = "Kodein-DI-Framework-Android"
    description = "Kodein DI Kotlin classes & extensions for Android"
    repo = "Kodein-DI"
}

dependencies {
    api(project(":core:kodein-di-core-jvm"))

    implementation("com.android.support:appcompat-v7:27.1.0")

    testImplementation("junit:junit:4.12")
}
