plugins {
    id("org.kodein.root")
}

allprojects {
    group = "org.kodein.di"
    version = "7.1.0"

    repositories {
        maven(url = "https://dl.bintray.com/kodein-framework/kodein-dev")
    }
}

kodeinPublications {
    repo = "Kodein-DI"
}
