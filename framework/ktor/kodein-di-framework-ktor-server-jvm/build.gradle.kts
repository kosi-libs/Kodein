plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven ("https://kotlin.bintray.com/ktor")
}

dependencies {
    api(project(":kodein-di-generic-jvm"))
    implementation("io.ktor:ktor-server-core:1.1.1")
}
