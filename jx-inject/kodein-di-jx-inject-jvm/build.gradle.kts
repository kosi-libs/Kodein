plugins {
    id("kodein-jvm")
}

dependencies {
    compile(project(":core:kodein-di-core-jvm"))
    compile("javax.inject:javax.inject:1")
    testCompile(project(":test-utils:test-utils-jvm"))
    testCompile(project(":generic:kodein-di-generic-jvm"))
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Jx-Inject-JVM"
        description = "Kodein DI extension that enables to auto-inject with JSR 330 javax.inject annotations for the JVM & Android"
        repo = "Kodein-DI"
    }
}
