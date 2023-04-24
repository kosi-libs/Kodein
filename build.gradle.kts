plugins {
    kodein.root
    alias(libs.plugins.ksp) apply false
}

kodein.experimentalCompose(libs.versions.compose)

allprojects {
    group = "org.kodein.di"
    version = "7.20.1"
}
