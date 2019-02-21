plugins {
    id("org.kodein.library.jvm")
}

repositories {
    maven ("https://kotlin.bintray.com/ktor")
}

kodeinLib {
    dependencies {
        api(project(":kodein-di-core") target "jvm")
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.1.1")
}
