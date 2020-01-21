import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("org.kodein.library.jvm")
    id("org.openjfx.javafxplugin") version "0.0.7"
}

kodeinLib {
    dependencies {
        api(project(":kodein-di") target "jvm")
    }
}

javafx {
    version = "12.0.1"
    modules("javafx.controls")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// TODO to be remove in 7.0 in benefit of kodein-internal-gradle 1.8 source compatibility
project.withConvention(JavaPluginConvention::class) {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("no.tornado:tornadofx:1.7.19")
    testImplementation("org.testfx:testfx-core:4.0.4-alpha")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}
