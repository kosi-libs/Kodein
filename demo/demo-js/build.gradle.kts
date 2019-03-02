import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("kotlin-platform-js")
    id("com.github.salomonbrys.gradle.kotlin.js.platform-assemble-web")
}

dependencies {
    implementation(project(":kodein-di-erased"))
    implementation(project(":demo:demo-common"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-js")
}

(tasks["compileKotlin2Js"] as Kotlin2JsCompile).apply {
    kotlinOptions.sourceMap = true
    kotlinOptions.moduleKind = "umd"
}

val assembleWeb = task<Sync>("assembleWeb") {
    group = "build"
    dependsOn("jsAssembleWeb")
    from("src/main/web")
    into("$buildDir/web")
    preserve {
        include("out/**")
    }
}

tasks["assemble"].dependsOn(assembleWeb)

assembleWeb {
    outputDir = "$buildDir/web/out"
}