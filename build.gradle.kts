plugins {
    id("org.kodein.root")
    alias(libs.plugins.ksp) apply false
}

allprojects {
    group = "org.kodein.di"
    version = "7.17.0"
}
