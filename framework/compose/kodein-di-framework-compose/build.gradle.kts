import com.android.build.gradle.internal.lint.LintModelWriterTask
import com.android.build.gradle.internal.tasks.LintModelMetadataTask

plugins {
    kodein.library.mppWithAndroid
    alias(kodeinGlobals.plugins.kotlin.plugin.compose)
    alias(libs.plugins.compose)
}

kotlin.kodein {
    jsEnv()
    allComposeUi()
    js() // Not embedded in allComposeUi

    common.mainDependencies {
        implementation(kotlin.compose.runtime)
        implementation(libs.jetbrains.lifecycle.viewmodel.compose)
        api(projects.kodeinDi)
    }

    android {
        sources.mainDependencies {
            api(projects.framework.android.kodeinDiFrameworkAndroidX)
            implementation(libs.android.x.lifecycle.viewmodel.compose)
        }
    }
}

// https://github.com/JetBrains/compose-multiplatform/issues/4739
tasks.withType<LintModelWriterTask>{
    dependsOn("generateResourceAccessorsForAndroidUnitTest")
}
tasks.withType<LintModelMetadataTask>{
    dependsOn("generateResourceAccessorsForAndroidUnitTest")
}

android {
    namespace = "org.kodein.di.compose"
}

kodeinUpload {
    name = "Kodein-Framework-Compose"
    description = "Kodein extensions for Jetpack / JetBrains Compose"
}