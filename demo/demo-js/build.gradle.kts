import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("kotlin-platform-js")
    id("com.github.salomonbrys.gradle.kjs.assemble-web")
}

dependencies {
    implementation(project(":kodein-di-erased"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
}

(getTasks()["compileKotlin2Js"] as Kotlin2JsCompile).apply {
    kotlinOptions.sourceMap = true
    kotlinOptions.moduleKind = "umd"
}
