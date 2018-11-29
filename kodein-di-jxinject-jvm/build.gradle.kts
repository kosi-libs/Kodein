plugins {
    id("org.kodein.library.jvm")
}

kodeinLib {
    dependencies {
        api(project(":kodein-di-core") target "jvm")
        api("javax.inject:javax.inject:1")

        testImplementation(project(":test-utils"))
        testImplementation(project(":kodein-di-generic-jvm"))
    }
}

kodeinUpload {
    name = "Kodein-DI-Jx-Inject"
    description = "KODEIN DI extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
