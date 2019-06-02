import org.jetbrains.kotlin.gradle.tasks.*

val tornadofxVersion = "1.7.18"

plugins {
    kotlin("jvm")
    application
    id("org.openjfx.javafxplugin") version "0.0.7"
}

application {
    mainClassName = "org.kodein.di.demo.tfx.TornadoApplication"
}

javafx {
    version = "12.0.1"
    modules("javafx.controls")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":kodein-di-generic-jvm"))
    implementation("no.tornado:tornadofx:$tornadofxVersion")
    implementation(project(":framework:tornadofx:kodein-di-framework-tornadofx-jvm"))
}