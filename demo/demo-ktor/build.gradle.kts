val ktorVersion = "1.1.1"
val logbackVersion = "1.2.3"

plugins {
    kotlin("jvm")
    application
}

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/ktor") }
}

application {
    mainClassName = "KodeinApplicationKt"
}

dependencies {
    fun ktor(module: String = "", version: String = ktorVersion) = "io.ktor:ktor$module:$version"

    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor())
    implementation(ktor("-server-netty"))
    implementation(ktor("-html-builder"))
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":kodein-di-generic-jvm"))
    implementation(project(":framework:ktor:kodein-di-framework-ktor-server-jvm"))
}

