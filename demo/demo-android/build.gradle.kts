plugins {
    id("com.android.application")
    id("kotlin-android")
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
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.0-rc-190")

    compile(project(":kodein-di-generic-jvm"))
    compile(project(":framework:android:kodein-di-framework-android-core"))
}
