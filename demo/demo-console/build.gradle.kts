import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    targets {
        jvm() {
            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }
        linuxX64 { binaries.executable() }
        macosX64 { binaries.executable() }
        mingwX64 { binaries.executable() }
    }

    sourceSets {
        getByName("commonMain").dependencies {
            implementation(project(":kodein-di-core"))
            implementation(project(":kodein-di-erased"))
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        }

        getByName("jvmMain").dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
        }
    }
}
