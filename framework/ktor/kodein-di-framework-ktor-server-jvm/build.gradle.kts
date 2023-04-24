plugins {
    kodein.library.jvm
}

dependencies {
    api(projects.kodeinDi)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.sessions)
    testImplementation(libs.ktor.test.server)
    testImplementation(libs.ktor.test.server.default.headers)
}

kodeinUpload {
    name = "Kodein-Framework-Ktor"
    description = "Kodein classes & extensions for Ktor."
}