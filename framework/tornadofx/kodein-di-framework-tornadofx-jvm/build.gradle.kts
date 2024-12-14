plugins {
    kodein.library.jvm
    alias(libs.plugins.openjfx)
}

javafx {
    version = libs.versions.javafx.get()
    modules("javafx.controls")
}

dependencies {
    api(projects.kodeinDi)
    implementation(kodeinGlobals.kotlin.reflect)
    implementation(libs.tornadofx)
    testImplementation(kotlin("test"))
    testImplementation(libs.testfx)
    testImplementation(libs.junit.jupiter)
}

kodeinUpload {
    name = "Kodein-Framework-TornadoFX"
    description = "Kodein Kotlin classes & extensions for the TornadoFX framework"
}