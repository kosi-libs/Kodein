import com.github.salomonbrys.gradle.kjs.jstests.*
import org.kodein.internal.gradle.*

plugins {
    id("org.kodein.library")
}

kotlin {
    addAll(listOf("jvm") + kodeinNative.targets)
    add("js") {
        mainJsCompileTask.kotlinOptions.moduleKind = "umd"
        addKotlinJSTest()
    }

    sourceSets.apply {
        val commonMain = getByName("commonMain") {
            dependencies {
                api(project(":kodein-core"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:${kodeinVersions.kotlin}")
            }
            languageSettings.apply {
                progressiveMode = true
            }
        }

        getByName("commonTest") {
            dependencies {
                implementation(project(":test-utils"))
            }
        }

        getByName("jvmMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:${kodeinVersions.kotlin}")
            }
        }

        getByName("jvmTest") {
            dependencies {
                implementation("com.google.inject:guice:4.1.0")
                implementation("org.jetbrains.kotlin:kotlin-reflect:${kodeinVersions.kotlin}")
            }
        }

        getByName("jsMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-js:${kodeinVersions.kotlin}")
            }
        }

        val allNative = create("allNative") {
            dependsOn(commonMain)
        }

        configureAll(kodeinNative.sourceSets) {
            dependsOn(allNative)
        }

    }

}
