plugins {
    kodein.library.jvm
}

dependencies {
    api(projects.kodeinDi)
    api(libs.javax.inject)

    testImplementation(projects.testUtils)
}

kodeinUpload {
    name = "Kodein-JxInject"
    description = "Kodein extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
