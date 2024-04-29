import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("org.kodein.library.mpp")
}

kotlin.kodein {
    all()

    common {
        mainDependencies {
            api(libs.kosi.kaverit)
        }
        testDependencies {
            implementation(projects.testUtils)
        }
    }

    jvm {
        target.setCompileClasspath()
    }
}

//kotlin {
//    @OptIn(ExperimentalKotlinGradlePluginApi::class)
//    compilerOptions {
//        freeCompilerArgs.add("-Xexpect-actual-classes")
//    }
//}

kodeinUpload {
    name = "Kodein"
    description = "Kodein Core"
}
