plugins {
    id("org.kodein.mpp")
    id("com.google.devtools.ksp")
}

kodein {
    kotlin {
        common.main {
            dependencies {
                implementation(project(":compiler:kodein-resolver-api"))
            }
        }
        common.test {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
        }
        add(kodeinTargets.native.all)
        add(kodeinTargets.js.js)
    }
}

dependencies {
    listOf(
        "kspCommonMainMetadata",
        "kspJvm", "kspJs",
        "kspMacosX64", "kspMacosArm64",
        "kspIosArm32", "kspIosArm64", "kspIosX64", "kspIosSimulatorArm64",
        "kspWatchosArm32", "kspWatchosArm64", "kspWatchosX86", "kspWatchosSimulatorArm64",
        "kspTvosArm64", "kspTvosX64", "kspTvosSimulatorArm64",
        "kspLinuxX64", "kspLinuxArm64", "kspLinuxArm32Hfp",
        // "mingwX64"
    ).forEach {
        add(it, project(":compiler:kodein-resolver-processor"))
    }
}
