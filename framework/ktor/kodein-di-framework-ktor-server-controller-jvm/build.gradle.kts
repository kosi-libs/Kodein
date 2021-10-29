val ktorVersion = "2.0.0-beta-1"

plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

dependencies {
    fun ktor(module: String = "", version: String = ktorVersion) = "io.ktor:ktor$module:$version"

    api(project(":framework:ktor:kodein-di-framework-ktor-server-jvm"))
            
    implementation(ktor("-server-core"))
    testImplementation(ktor("-server-tests"))
    testImplementation(ktor("-server-default-headers"))
}

kodeinUpload {
    name = "Kodein-DI-Framework-Ktor"
    description = "Kodein-DI Kotlin classes & extensions for Ktor, with architectural helpers"
}