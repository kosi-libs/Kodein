val ktorVersion = "1.1.1"
val logbackVersion = "1.2.3"

plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven("https://kotlin.bintray.com/ktor")
}

kodeinLib {
    dependencies {
        api(project(":kodein-di") target "jvm")
    }
}

dependencies {
    fun ktor(module: String = "", version: String = ktorVersion) = "io.ktor:ktor$module:$version"

    implementation(ktor())
    implementation(ktor("-server-core"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(ktor("-server-tests"))
}
