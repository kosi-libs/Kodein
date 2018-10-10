plugins {
    id("kotlin-platform-common")
    id("kodein-versions")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-common:${kodeinVersions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test-common:${kodeinVersions.kotlin}")
    compile("org.jetbrains.kotlin:kotlin-test-annotations-common:${kodeinVersions.kotlin}")
}
