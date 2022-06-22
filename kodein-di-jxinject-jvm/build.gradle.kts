plugins {
    id("org.kodein.library.jvm")
}

dependencies {
    api(project(":kodein-di"))
    api("javax.inject:javax.inject:1")

    testImplementation(project(":test-utils"))
}

kodeinUpload {
    name = "Kodein-JxInject"
    description = "Kodein extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
