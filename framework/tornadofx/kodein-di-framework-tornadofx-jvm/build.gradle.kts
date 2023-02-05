import org.jetbrains.kotlin.gradle.tasks.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.kodein.library.jvm")
    alias(libs.plugins.openjfx)
}

javafx {
    version = libs.versions.javafx.version.get()
    modules("javafx.controls")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(projects.kodeinDi)
    implementation(kodeinGlobals.kotlin.reflect)
    implementation(libs.tornadofx)
    testImplementation(libs.testfx)
    testImplementation(libs.junit.jupiter)
}

kodeinUpload {
    name = "Kodein-Framework-TornadoFX"
    description = "Kodein Kotlin classes & extensions for the TornadoFX framework"
}