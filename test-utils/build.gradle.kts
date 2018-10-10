import com.github.salomonbrys.gradle.kjs.jstests.mainJsCompileTask
import org.kodein.internal.gradle.add
import org.kodein.internal.gradle.addAll
import org.kodein.internal.gradle.configureAll

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.kodein.versions")
    id("org.kodein.native")
    id("com.github.salomonbrys.gradle.kjs.js-tests")
}

kotlin {
    addAll(listOf("jvm") + kodeinNative.targets)
    add("js") {
        mainJsCompileTask.kotlinOptions.moduleKind = "umd"
    }

    sourceSets.apply {
        val commonMain = getByName("commonMain") {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test-common:${kodeinVersions.kotlin}")
                api("org.jetbrains.kotlin:kotlin-test-annotations-common:${kodeinVersions.kotlin}")
                api("org.jetbrains.kotlin:kotlin-stdlib-common:${kodeinVersions.kotlin}")
            }
            languageSettings.apply {
                progressiveMode = true
            }
        }

        getByName("jvmMain") {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test:${kodeinVersions.kotlin}")
                api("org.jetbrains.kotlin:kotlin-test-junit:${kodeinVersions.kotlin}")
                api("org.jetbrains.kotlin:kotlin-stdlib:${kodeinVersions.kotlin}")
                api("junit:junit:4.12")
            }
        }

        getByName("jsMain") {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-test-js:${kodeinVersions.kotlin}")
                api("org.jetbrains.kotlin:kotlin-stdlib-js:${kodeinVersions.kotlin}")
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
