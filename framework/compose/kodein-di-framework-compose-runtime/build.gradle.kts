plugins {
    kodein.library.mppWithAndroid
    alias(kodeinGlobals.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnv()
    allComposeRuntime()

    common.mainDependencies {
        implementation(libs.jetbrains.compose.runtime)
        api(projects.kodeinDi)
    }

    android {
        target.namespace = "org.kodein.di.compose.runtime"

        sources.mainDependencies {
            implementation(libs.jetbrains.compose.ui)
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
        }
    }

    jvm {
        sources.testDependencies {
            implementation(kotlin.compose.desktop.currentOs)
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose-Runtime"
    description =
        "Kodein extensions for JetBrains Compose runtime targets (Android / JVM / Native / JS / WASM)"
}
