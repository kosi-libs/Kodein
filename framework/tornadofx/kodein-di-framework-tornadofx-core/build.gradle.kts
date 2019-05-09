val tornadofxVersion = "1.7.18"

plugins {
    id("org.kodein.library.jvm")
}

repositories {
    mavenCentral()
}

kodeinLib {
    dependencies {
        api(project(":kodein-di-core") target "jvm")
    }
}

dependencies {
    implementation("no.tornado:tornadofx:$tornadofxVersion")
}
