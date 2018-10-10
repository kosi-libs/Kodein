plugins {
    id("org.kodein.library.android")
}

//kodeinPublication {
//    upload {
//        name = "Kodein-DI-Framework-Android-Core"
//        description = "Kodein DI Kotlin classes & extensions for the Android platform"
//        repo = "Kodein-DI"
//    }
//}

dependencies {
    api(project(":kodein-di-core"))
}
