plugins {
    id("org.kodein.library.android")
}

//kodeinPublication {
//    upload {
//        name = "Kodein-DI-Framework-Android-X"
//        description = "Kodein DI Kotlin classes & extensions for the AndroidX library"
//        repo = "Kodein-DI"
//    }
//}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation("androidx.appcompat:appcompat:1.0.0-alpha1")
}
