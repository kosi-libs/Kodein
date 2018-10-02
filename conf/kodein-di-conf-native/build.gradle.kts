import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent

plugins {
    id("kodein-native")
}

dependencies {
    expectedBy(project(":conf:kodein-di-conf-common"))
    implementation(project(":core:kodein-di-core-native"))
    testImplementation(project(":test-utils:test-utils-native"))
    testImplementation(project(":erased:kodein-di-erased-native"))
}

components.getByName("main") {
    this as KotlinNativeMainComponent
    outputKinds.set(listOf(KotlinNativeMainComponent.KLIBRARY))
    targets = kodeinNative.allTargets
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Conf-Native"
        description = "KODEIN Dependency Injection that can be configured / mutated for Native Platforms"
        repo = "Kodein-DI"
    }
}
