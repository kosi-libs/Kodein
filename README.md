[![Maven Central](https://img.shields.io/maven-central/v/org.kodein.di/kodein-di)](https://mvnrepository.com/artifact/org.kodein.di/kodein-di)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
![Github Actions](https://github.com/kosi-libs/Kodein/actions/workflows/snapshot.yml/badge.svg)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/kosi-libs/Kodein/blob/master/LICENSE.txt)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-green.svg?style=flat&logo=slack)](https://kotlinlang.slack.com/messages/kodein/)

KOtlin DEpendency INjection
===========================

**_KODEIN_** is a straightforward and yet very useful dependency retrieval container. it is effortless to use and configure.

**_KODEIN_** works on all Kotlin Multiplatform targets:

- JVM / Android.
- all Native platforms (iOS, macOS, Linux, Windows, WebAssembly).
- Javascript / WasmJs.

**_KODEIN_** allows you to:

- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Bind classes or interfaces to their instance or provider
- Debug your dependency bindings and recursions

**_KODEIN_** provides extensions to be integrable into:

- [Android](https://developer.android.com/)
- [Compose (Android / Desktop / Web)](https://kosi-libs.org/kodein/7.22/framework/compose.html)
- [Ktor](https://ktor.io/)

An example is always better than a thousand words:

```kotlin
val di = DI {
    bindProvider<Dice> { RandomDice(0, 5) }
    bindSingleton<DataSource> { SqliteDS.open("path/to/file") }
}

class Controller(private di: DI) {
    private val ds: DataSource by di.instance()
}
```

**_KODEIN_** is a good choice because:

- It proposes a very simple and readable declarative DSL
- It is not subject to type erasure (as Java is)
- It integrates nicely with Android
- It proposes a very kotlin-esque idiomatic API
- It is fast and optimized (makes extensive use of `inline`)

Installation
---------

Kodein is available on Maven Central.

```kotlin
repositories {
    mavenCentral()
}
```

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.kodein.di:kodein-di:{version}")
            }
        }
    }
}
```

**_KODEIN_** 7+ is the current major version, but documentation is available for previous versions.

**[Kodein documentation](https://kosi-libs.org/kodein/)**


Kotlin & JVM compatibility
---------

|   Kodein    | Kotlin |   JDK   |
|:-----------:|:------:|:-------:|
|   7.22      | 2.0.+  | min 11  |
|   7.21      | 1.9.+  | min 1.8 |
|   7.20      | 1.8.10 | min 1.8 |
|   7.19      | 1.8.10 | min 1.8 |
|   7.18      | 1.8.0  | min 1.8 |
|   7.17      | 1.8.0  | min 1.8 |

> Full table can be found [here](https://kosi-libs.org/kodein/7.22/core/platform-and-genericity.html)

Support
-------

Support is held in the [Kodein Slack channel](https://kotlinlang.slack.com/messages/kodein/)
(you can get an invitation to the Kotlin Slack [here](https://slack.kotlinlang.org/)).

[//]: # (Testimonies)

[//]: # (-----------)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> At [Collokia]&#40;https://www.collokia.com/&#41; we use Kodein in all of our backend service infrastructure and all modules in those services are loosely coupled through injection with Kodein.)

[//]: # (> It allows us to have nice module independence, and to opt-out of injection during testing or build separate modules in support of testing.  )

[//]: # (> It is a key component and building block in our architecture.  )

[//]: # (> -- [Jayson Minard]&#40;https://github.com/apatrida&#41;)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> At [Moovel Group GmbH]&#40;https://www.moovel.com/de/en&#41;, we have successfully used the wonderful Kodein library into in [this Android app]&#40;https://play.google.com/store/apps/details?id=com.daimler.moovel.android&#41;.)

[//]: # (> As we improved it, we found Kodein to be much more useful than Dagger2 as it simplified our code throughout.  )

[//]: # (> Kodein is in my view, much easier to understand, doesn't have that nasty ceremony, and has really nice debug messages.  )

[//]: # (> We are also working now on other projects where we are using Kodein as well.  )

[//]: # (> -- [Sorin Albu-Irimies]&#40;https://github.com/sorinirimies&#41;)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> Kodein has been instrumental in moving our entire production application to Kotlin at [InSite Applications]&#40;https://insiteapplications.com&#41;. It uses standard Kotlin idioms and features for ultimate clarity and simplicity. It was clear to us from the beginning that Kodein would be our DI solution. Our devs love it so much that they've transitioned to using it in their personal apps, both Java and Kotlin!  )

[//]: # (> -- [Eliezer Graber]&#40;https://github.com/eygraber&#41;)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> At [Compsoft Creative]&#40;https://www.compsoft.co.uk&#41;, Kodein is central our new Kotlin based app architecture, giving us a solid underpinning to all apps we develop and allowing a simple yet powerful way to de-couple our services with a library that is lightweight and perfect for mobile apps.  )

[//]: # (> -- [Daniel Payne]&#40;https://github.com/CompsoftCreative&#41;)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> Kodein is used in [the android app of the OhelShem school]&#40;https://github.com/OhelShem/android&#41;.  )

[//]: # (> -- [Yoav Sternberg]&#40;https://github.com/yoavst&#41;&nbsp;)

[//]: # ()
[//]: # (&nbsp;)

[//]: # ()
[//]: # (> Kodein was created at [Dental Monitoring]&#40;https://dental-monitoring.com/&#41; with the opinion that Dagger2 is way too verbose and complex.)

[//]: # (> It is now used in almost all our projects: the server, the internal production software & the Android application.  )

[//]: # (> Kodein is very easy to use and set up: it allows our team to easily share code and patterns, as well as quickly bootstrapping new ideas.  )

[//]: # (> -- [Salomon Brys]&#40;https://github.com/SalomonBrys&#41;)

[//]: # ()
[//]: # (&nbsp;)

If you are using KODEIN, please [let us know](mailto:contact@kodein.net)!

## Supported by

[![JetBrains logo.](https://resources.jetbrains.com/storage/products/company/brand/logos/jetbrains.svg)](https://jb.gg/OpenSourceSupport)