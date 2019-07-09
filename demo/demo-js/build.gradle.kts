import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("js")
}

dependencies {
    implementation(project(":kodein-di-erased"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
}

kotlin {
    target {
        browser()
    }
}
