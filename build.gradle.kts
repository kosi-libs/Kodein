plugins {
    kodein.root
    alias(libs.plugins.ksp) apply false
}

kodein.experimentalCompose(libs.versions.jbCompose)

allprojects {
    group = "org.kodein.di"
    version = "7.21.0"
}
