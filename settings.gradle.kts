buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-Internal-Gradle")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:2.3.1")
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

if (excludeAndroid != "true") {
    include(
            ":framework:android:kodein-di-framework-android-core",
            ":framework:android:kodein-di-framework-android-support",
            ":framework:android:kodein-di-framework-android-x",

            ":demo:demo-android"
    )
}

val excludeKtor: String? by settings

if (excludeKtor != "true") {
    include(
        ":framework:ktor:kodein-di-framework-ktor-server-jvm",

        ":demo:demo-ktor"
    )
}
