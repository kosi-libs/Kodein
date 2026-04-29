plugins {
    kodein.root
    alias(libs.plugins.ksp) apply false
}

allprojects {
    group = "org.kodein.di"
    version = "7.32.0"
}

kodeinUploadRoot {
    githubProjectName = "Kodein"
}
