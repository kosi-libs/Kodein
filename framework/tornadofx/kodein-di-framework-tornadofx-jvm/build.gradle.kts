val tornadofxVersion = "1.7.18"

plugins {
    id("org.kodein.library.jvm")
    id("org.openjfx.javafxplugin") version "0.0.7"
}

kodeinLib {
    dependencies {
        api(project(":kodein-di-core") target "jvm")
    }
}

javafx {
    version = "12.0.1"
    modules("javafx.controls")
}

dependencies {
    implementation("no.tornado:tornadofx:$tornadofxVersion")
}
