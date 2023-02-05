@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.kodein.root")
    alias(libs.plugins.ksp) apply false
}

allprojects {
    group = "org.kodein.di"
    version = "7.19.0"
}
