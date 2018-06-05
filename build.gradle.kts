buildscript {

    repositories {
        jcenter()
        google()
        mavenLocal()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/salomonbrys/KMP-Gradle-Utils")
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
    version = "5.0.0"
}

val travisBuild by extra { System.getenv("TRAVIS") == "true" }
