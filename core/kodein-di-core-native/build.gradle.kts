import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent

plugins {
    id("kodein-native")
}

dependencies {
    expectedBy(project(":core:kodein-di-core-common"))
}

components.getByName("main") {
    this as KotlinNativeMainComponent
    outputKinds.set(listOf(KotlinNativeMainComponent.KLIBRARY))
    targets = kodeinNative.allTargets
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Core-Native"
        description = "KODEIN Dependency Injection Core for Native Platforms"
        repo = "Kodein-DI"
    }
}
