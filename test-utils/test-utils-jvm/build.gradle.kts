plugins {
    id("kotlin-platform-jvm")
    id("kodein-versions")
}

dependencies {
    expectedBy(project(":test-utils:test-utils-common"))
    compile("org.jetbrains.kotlin:kotlin-stdlib:${kodeinVersions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test:${kodeinVersions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test-junit:${kodeinVersions.kotlin}")

    compile("junit:junit:4.12")
}
