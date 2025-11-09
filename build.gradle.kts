plugins {
    kodein.root
    alias(libs.plugins.ksp) apply false
}

allprojects {
    group = "org.kodein.di"
    version = "7.28.0"
}
