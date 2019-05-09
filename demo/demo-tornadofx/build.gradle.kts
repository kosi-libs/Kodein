val tornadofxVersion = "1.7.18"

plugins {
    kotlin("jvm")
    application
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "org.kodein.di.demo.tfx.TornadoApplication"
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":kodein-di-generic-jvm"))
    implementation("no.tornado:tornadofx:$tornadofxVersion")
    implementation(project(":framework:tornadofx:kodein-di-framework-tornadofx-core"))
}