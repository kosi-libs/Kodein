plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

dependencies {
    api(project(":kodein-di"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.sessions)
    testImplementation(libs.ktor.test.server)
    testImplementation(libs.ktor.test.server.default.headers)
}

kodeinUpload {
    name = "Kodein-Framework-Ktor"
    description = "Kodein classes & extensions for Ktor."
}