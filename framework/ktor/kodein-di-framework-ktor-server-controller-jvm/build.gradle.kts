plugins {
    kodein.library.jvm
}

dependencies {
    api(projects.framework.ktor.kodeinDiFrameworkKtorServerJvm)

    implementation(libs.ktor.server.core)
    testImplementation(libs.ktor.test.server)
    testImplementation(libs.ktor.test.server.default.headers)
}

kodeinUpload {
    name = "Kodein-Framework-Ktor"
    description = "Kodein classes & extensions for Ktor, with architectural helpers"
}