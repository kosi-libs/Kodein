plugins {
    id("org.kodein.library.jvm")
}

dependencies {
    implementation(libs.ksp.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(project(":compiler:kodein-resolver-api"))
}
