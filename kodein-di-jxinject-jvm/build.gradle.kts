plugins {
    id("org.kodein.library.jvm")
}

dependencies {
    api(project(":kodein-di-core"))
    api("javax.inject:javax.inject:1")

    testImplementation(project(":test-utils"))
    testImplementation(project(":kodein-di-generic-jvm"))
}

kodeinUpload {
    name = "Kodein-DI-Jx-Inject"
    description = "KODEIN DI extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
