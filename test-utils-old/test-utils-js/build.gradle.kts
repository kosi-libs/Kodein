import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("kotlin-platform-js")
    id("kodein-versions")
}

dependencies {
    expectedBy(project(":test-utils:test-utils-common"))
    compile("org.jetbrains.kotlin:kotlin-stdlib-js:${kodeinVersions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test-js:${kodeinVersions.kotlin}")
}

(tasks["compileKotlin2Js"] as Kotlin2JsCompile).apply {
    kotlinOptions.moduleKind = "umd"
}
