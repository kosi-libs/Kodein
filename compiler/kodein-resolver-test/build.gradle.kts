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
                // Adding KSP JVM result to COMMON source set
                kotlin.srcDir("build/generated/ksp/jvm/jvmTest/kotlin")
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
    add("kspCommonMainMetadata", project(":compiler:kodein-resolver-processor"))
    add("kspJvm", project(":compiler:kodein-resolver-processor"))
    add("kspJs", project(":compiler:kodein-resolver-processor"))
    add("kspMacosArm64", project(":compiler:kodein-resolver-processor"))
    add("kspIosSimulatorArm64", project(":compiler:kodein-resolver-processor"))
    add("kspTvosSimulatorArm64", project(":compiler:kodein-resolver-processor"))
    add("kspWatchosSimulatorArm64", project(":compiler:kodein-resolver-processor"))
}
