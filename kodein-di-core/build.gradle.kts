import com.github.salomonbrys.gradle.kjs.jstests.mainJsCompileTask
import org.kodein.internal.gradle.add
import org.kodein.internal.gradle.addAll
import org.kodein.internal.gradle.configureAll

plugins {
    id("org.kodein.library.mpp")
}

kotlin {
    addAll(listOf("jvm") + kodeinNative.targets)
    add("js") {
        mainJsCompileTask.kotlinOptions.moduleKind = "umd"
    }

    sourceSets.apply {
        val commonMain = getByName("commonMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:${kodeinVersions.kotlin}")
            }
            languageSettings.apply {
                progressiveMode = true
            }
        }

        getByName("jvmMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:${kodeinVersions.kotlin}")
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
