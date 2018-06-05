import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("kotlin-platform-js")
    id("assemble-web")
    id("kodein-versions")
}

dependencies {
    implementation(project(":erased:kodein-di-erased-js"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js:${kodeinVersions.kotlin}")
}

(getTasks()["compileKotlin2Js"] as Kotlin2JsCompile).apply {
//    kotlinOptions.outputFile = "${projectDir}/web/out/demo.js"
    kotlinOptions.sourceMap = true
    kotlinOptions.moduleKind = "umd"
}
