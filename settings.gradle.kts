import org.kodein.internal.gradle.settings.*

buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://raw.githubusercontent.com/kosi-libs/kodein-internal-gradle-plugin/mvn-repo")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:7.0.6")
    }
}

apply { plugin("org.kodein.settings") }

rootProject.name = "Kodein"

include(
        ":test-utils",
        ":kodein-di",
        ":kodein-di-conf",
        ":kodein-di-jxinject-jvm",
        ""
)

// TODO: WIP - Disabled to avoid breaking CI workflow
//include(
//    ":compiler:kodein-resolver-processor",
//    ":compiler:kodein-resolver-api",
//    ":compiler:kodein-resolver-test",
//)

android.include(
        ":framework:android:kodein-di-framework-android-core",
        ":framework:android:kodein-di-framework-android-support",
        ":framework:android:kodein-di-framework-android-x",
        ":framework:android:kodein-di-framework-android-x-viewmodel",
        ":framework:android:kodein-di-framework-android-x-viewmodel-savedstate",
        ":framework:android:kodein-di-framework-android-compose"
)

framework("ktor").include(
        ":framework:ktor:kodein-di-framework-ktor-server-jvm",
        ":framework:ktor:kodein-di-framework-ktor-server-controller-jvm"
)

framework("tfx").include(
        ":framework:tornadofx:kodein-di-framework-tornadofx-jvm"
)

framework("compose").include(
        ":framework:compose:kodein-di-framework-compose"
)
