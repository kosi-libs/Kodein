plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        applicationId = "kodein.demo"
        minSdkVersion(15)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.0")

    implementation("androidx.appcompat:appcompat:1.0.1")

    implementation(project(":kodein-di-generic-jvm"))
    implementation(project(":framework:android:kodein-di-framework-android-x"))
}
