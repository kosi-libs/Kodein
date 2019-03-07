val ktorVersion = "1.1.2"
val logbackVersion = "1.2.3"

plugins {
    kotlin("jvm")
    application
}

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/ktor") }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

dependencies {
    fun ktor(module: String = "", version: String = ktorVersion) = "io.ktor:ktor$module:$version"

    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor())
    implementation(ktor("-server-netty"))
    implementation(ktor("-locations"))
    implementation(ktor("-freemarker"))
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation(project(":kodein-di-generic-jvm"))
    implementation(project(":demo:demo-common")) {
        exclude(group = "org.kodein")
    }
    implementation(project(":framework:ktor:kodein-di-framework-ktor-server-jvm"))
}

