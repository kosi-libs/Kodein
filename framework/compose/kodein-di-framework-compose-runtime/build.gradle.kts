@file:OptIn(ExperimentalComposeLibrary::class)

import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kodein.library.mppWithAndroid
    alias(kodeinGlobals.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnv()
    allComposeRuntime()

    common.mainDependencies {
        implementation(kotlin.compose.runtime)
        api(projects.kodeinDi)
    }

    common.testDependencies {
        @OptIn(ExperimentalComposeLibrary::class) implementation(kotlin.compose.uiTest)
        implementation(kotlin.compose.foundation)
        implementation(kotlin.compose.material3)
        implementation(kotlin.compose.ui)
    }

    android {
        sources.mainDependencies {
            implementation(kotlin.compose.ui)
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
        }
    }

    jvm {
        sources.testDependencies {
            implementation(kotlin.compose.desktop.currentOs)
        }
    }
}

dependencies {
    androidTestImplementation(libs.ui.test.junit4.android)
    debugImplementation(libs.ui.test.manifest)
}

android {
    namespace = "org.kodein.di.compose.runtime"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Runtime"
    description = "Kodein extensions for JetBrains Compose runtime targets (Android / JVM / Native / JS / WASM)"
}
