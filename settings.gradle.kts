import org.kodein.internal.gradle.settings.*

buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://dl.bintray.com/kodein-framework/kodein-dev")
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-Internal-Gradle")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:4.0.0")
    }
}

apply { plugin("org.kodein.settings") }

rootProject.name = "Kodein-DI"

include(
        ":test-utils",
        ":kodein-di",
        ":kodein-di-conf",
        ":kodein-di-jxinject-jvm",
        ""
)

android.include(
        ":framework:android:kodein-di-framework-android-core",
        ":framework:android:kodein-di-framework-android-support",
        ":framework:android:kodein-di-framework-android-x"
)

framework("ktor").include(
        ":framework:ktor:kodein-di-framework-ktor-server-jvm",
        ":framework:ktor:kodein-di-framework-ktor-server-controller-jvm"
)

framework("tfx").include(
        ":framework:tornadofx:kodein-di-framework-tornadofx-jvm"
)
