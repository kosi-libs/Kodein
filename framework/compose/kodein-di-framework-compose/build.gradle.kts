plugins {
    kodein.library.mppWithAndroid
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnv()

    allComposeExperimental()

    common.mainDependencies {
        compileOnly(kotlin.compose.runtime)
        api(projects.kodeinDi)
    }

    android {
        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
        }
    }
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}