plugins {
    id("org.kodein.library.jvm")
}

kodeinLib {
    dependencies {
        api(project(":kodein-di") target "jvm")
        api("javax.inject:javax.inject:1")

        testImplementation(project(":test-utils"))
        testImplementation(project(":kodein-di-generic-jvm"))
    }
}

// TODO to be remove in 7.0 in benefit of kodein-internal-gradle 1.8 source compatibility
tasks.withType<JavaCompile>() {
    sourceCompatibility = "1.7"
    targetCompatibility = "1.7"
}

kodeinUpload {
    name = "Kodein-DI-JxInject"
    description = "KODEIN DI extension that enables to auto-inject with JSR 330 javax.inject annotations"
}
