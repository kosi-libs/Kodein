plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    targets {
        jvm()
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
