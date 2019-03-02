import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeOutputKind

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    targets.apply {
        add(presets["jvm"].createTarget("jvm"))
        add(presets["js"].createTarget("js"))
        add(presets["linuxX64"].createTarget("linux").apply { (compilations["main"] as KotlinNativeCompilation).outputKind(NativeOutputKind.EXECUTABLE) })
        add(presets["macosX64"].createTarget("macos").apply { (compilations["main"] as KotlinNativeCompilation).outputKind(NativeOutputKind.EXECUTABLE) })
        add(presets["mingwX64"].createTarget("mingw").apply { (compilations["main"] as KotlinNativeCompilation).outputKind(NativeOutputKind.EXECUTABLE) })
    }

    sourceSets.apply {
        getByName("commonMain").dependencies {
            implementation(project(":kodein-di-core"))
            implementation(project(":kodein-di-erased"))
            implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        }
    }
}
