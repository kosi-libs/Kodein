plugins {
    id("org.kodein.library.android")
}

//kodeinPublication {
//    upload {
//        name = "Kodein-DI-Framework-Android-Support"
//        description = "Kodein DI Kotlin classes & extensions for the Android Support library"
//        repo = "Kodein-DI"
//    }
//}

dependencies {
    api(project(":framework:android:kodein-di-framework-android-core"))

    implementation("com.android.support:appcompat-v7:27.1.0")
}
