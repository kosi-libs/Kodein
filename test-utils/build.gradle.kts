plugins {
    id("org.kodein.mpp")
}

kotlin.kodein {
    all()

    common.mainDependencies {
        api(kodeinGlobals.kotlin.test)
    }

    jvm {
        sources.mainDependencies {
            api(kodeinGlobals.kotlin.test.junit)
        }
    }
}

kotlin.sourceSets {
    val jsBasedMain by getting
    getByName("wasmMain").dependsOn(jsBasedMain)
}
