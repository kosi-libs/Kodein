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
        implementation(libs.jetbrains.compose.runtime)
        implementation(libs.jetbrains.lifecycle.viewmodel.compose)
        api(projects.framework.compose.kodeinDiFrameworkComposeRuntime)
    }

    common.testDependencies {
        implementation(libs.jetbrains.compose.ui.test)
        implementation(libs.jetbrains.compose.foundation)
        implementation(libs.jetbrains.compose.ui)
    }

    android {
        target.namespace = "org.kodein.di.compose"

        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
        }
    }

    jvm {
        this.sources.testDependencies {
            implementation(kotlin.compose.desktop.currentOs)
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}
