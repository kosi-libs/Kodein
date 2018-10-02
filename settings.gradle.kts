
rootProject.name = "Kodein-DI"

enableFeaturePreview("GRADLE_METADATA")

include(
        ":core:kodein-di-core-common",
        ":core:kodein-di-core-jvm",
        ":core:kodein-di-core-js",
        ":core:kodein-di-core-native",

        ":test-utils:test-utils-common",
        ":test-utils:test-utils-jvm",
        ":test-utils:test-utils-js",
        ":test-utils:test-utils-native",

        ":generic:kodein-di-generic-jvm",

        ":erased:kodein-di-erased-common",
        ":erased:kodein-di-erased-jvm",
        ":erased:kodein-di-erased-js",
        ":erased:kodein-di-erased-native",

        ":conf:kodein-di-conf-common",
        ":conf:kodein-di-conf-jvm",
        ":conf:kodein-di-conf-js",
        ":conf:kodein-di-conf-native",

        ":jx-inject:kodein-di-jx-inject-jvm",

        ":framework:android:kodein-di-framework-android-core",
        ":framework:android:kodein-di-framework-android-support",
        ":framework:android:kodein-di-framework-android-x",

        ":demo:demo-android",
        ":demo:demo-js",
        ":demo:demo-native",

        ""
)
