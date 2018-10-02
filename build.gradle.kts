buildscript {

    repositories {
        jcenter()
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/salomonbrys/KMP-Gradle-Utils")
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-Internal-Gradle")
        maven(url = "https://dl.bintray.com/salomonbrys/wup-digital-maven")
        mavenLocal()
    }

    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-plugin:1.3.0")
    }

}

allprojects {
    repositories {
        jcenter()
        google()
    }

    group = "org.kodein.di"
    version = "5.3.0"
}

val travisBuild by extra { System.getenv("TRAVIS") == "true" }
