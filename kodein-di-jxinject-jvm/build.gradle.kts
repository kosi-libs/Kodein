plugins {
    id("org.kodein.library.jvm")
}

dependencies {
    api(project(":kodein-di"))
    api("javax.inject:javax.inject:1")

    testImplementation(project(":test-utils"))
}

kodeinUpload {
    name = "Kodein-DI-JxInject"
    description = "KODEIN DI extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
