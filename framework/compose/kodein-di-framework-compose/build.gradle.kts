import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    kodein.library.mppWithAndroid
    alias(kodeinGlobals.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnvBrowserOnly()
    allComposeUi()
    js {
        target.browser {
            testTask { enabled = false }
        }
    }

    common.mainDependencies {
        implementation(kotlin.compose.runtime)
        implementation(libs.jetbrains.lifecycle.viewmodel.compose)
        api(projects.framework.compose.kodeinDiFrameworkComposeRuntime)
    }

//    common.testDependencies {
//        @OptIn(ExperimentalComposeLibrary::class) implementation(kotlin.compose.uiTest)
//        implementation(kotlin.compose.foundation)
//        implementation(kotlin.compose.material3)
//        implementation(kotlin.compose.ui)
//    }

    android {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        target.instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
        }
    }

    jvm {
        sources.testDependencies {
            implementation(kotlin.compose.desktop.currentOs)
        }
    }
}

//dependencies {
//    androidTestImplementation(libs.ui.test.junit4.android)
//    debugImplementation(libs.ui.test.manifest)
//}

android {
    namespace = "org.kodein.di.compose"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests {
            all {
                it.exclude("org/kodein/di/compose")
            }
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}
