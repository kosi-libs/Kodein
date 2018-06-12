buildscript {

    repositories {
        jcenter()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/salomonbrys/KMP-Gradle-Utils")
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-Internal-Gradle")
    }

    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-plugin:1.0.0")
    }

}

allprojects {
    repositories {
        jcenter()
        google()
    }

    group = "org.kodein.di"
    version = "5.0.1"
}

val travisBuild by extra { System.getenv("TRAVIS") == "true" }
