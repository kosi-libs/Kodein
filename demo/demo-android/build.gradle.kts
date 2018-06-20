plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kodein-versions")
}

android {
    compileSdkVersion(27)
    buildToolsVersion("27.0.3")

    defaultConfig {
        applicationId = "kodein.demo"
        minSdkVersion(15)
        versionCode = 1
        versionName = "1.0"
    }

    dexOptions {
        val travisBuild = System.getenv("TRAVIS") == "true"
        val preDexEnabled = System.getProperty("pre-dex", "true") == "true"
        preDexLibraries = preDexEnabled && !travisBuild
    }

    packagingOptions {
        exclude("META-INF/main.kotlin_module")
    }

}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kodeinVersions.kotlin}")

  implementation(project(":generic:kodein-di-generic-jvm"))
  implementation(project(":framework:android:kodein-di-framework-android-core"))
}
