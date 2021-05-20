val ktorVersion = "1.5.4"

plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven("https://kotlin.bintray.com/ktor")
}

dependencies {
    fun ktor(module: String = "", version: String = ktorVersion) = "io.ktor:ktor$module:$version"

    api(project(":kodein-di"))
    implementation(ktor())
    implementation(ktor("-server-core"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kodeinVersions.kotlin}")
    testImplementation(ktor("-server-tests"))
}

kodeinUpload {
    name = "Kodein-DI-Framework-Ktor"
    description = "Kodein-DI Kotlin classes & extensions for Ktor."
}