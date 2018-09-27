plugins {
    id("kotlin-platform-native")
}

dependencies {
    expectedBy(project(":test-utils:test-utils-common"))
}
