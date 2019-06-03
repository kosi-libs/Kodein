plugins {
    id("org.kodein.root")
}

allprojects {
    group = "org.kodein.di"
    version = "6.3.0"
}

kodeinPublications {
    repo = "Kodein-DI"
}

// see https://github.com/gradle/kotlin-dsl/issues/607#issuecomment-375687119
subprojects { parent!!.path.takeIf { it != rootProject.path }?.let { evaluationDependsOn(it)  } }