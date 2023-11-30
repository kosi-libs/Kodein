plugins {
    kodein.library.mppWithAndroid
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnv()

//    allComposeStable()
    // 1.5.11 does not work with compose WasmJS yet
    allComposeExperimental()

    common.mainDependencies {
        implementation(kotlin.compose.runtime)
        api(projects.kodeinDi)
    }

    android {
        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
        }
    }
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.compiler.get())
}

android {
    namespace = "org.kodein.di.compose"
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}