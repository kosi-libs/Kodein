buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-Internal-Gradle")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:2.7.0")
    }
}

apply { plugin("org.kodein.settings") }

rootProject.name = "Kodein-DI"

include(
        ":kodein-di-core",

        ":test-utils",

        ":kodein-di-generic-jvm",

        ":kodein-di-erased",

        ":kodein-di-conf",

        ":kodein-di-jxinject-jvm",

        ":demo:demo-console",
        ":demo:demo-js",

        ""
)

val excludeAndroid: String? by settings

if ((excludeAndroid ?: System.getenv("EXCLUDE_ANDROID")) != "true") {
    include(
            ":framework:android:kodein-di-framework-android-core",
            ":framework:android:kodein-di-framework-android-support",
            ":framework:android:kodein-di-framework-android-x",

            ":demo:demo-android"
    )
}

val excludeKtor: String? by settings

if ((excludeKtor  ?: System.getenv("EXCLUDE_KTOR")) != "true") {
    include(
            ":framework:ktor:kodein-di-framework-ktor-server-jvm",
            ":framework:ktor:kodein-di-framework-ktor-server-controller-jvm",

            ":demo:demo-ktor"
    )
}

val excludeTFX: String? by settings

if ((excludeTFX ?: System.getenv("EXCLUDE_TFX")) != "true") {
    include(
            ":framework:tornadofx:kodein-di-framework-tornadofx-jvm"
    )
}
