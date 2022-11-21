import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("org.kodein.library.jvm")
    alias(libs.plugins.openjfx)
}

javafx {
    version = libs.versions.javafx.version.get()
    modules("javafx.controls")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(":kodein-di"))
    implementation(libs.tornadofx)
    implementation(libs.kotlin.reflect)
    testImplementation(libs.testfx)
    testImplementation(libs.junit.jupiter)
}

kodeinUpload {
    name = "Kodein-Framework-TornadoFX"
    description = "Kodein Kotlin classes & extensions for the TornadoFX framework"
}