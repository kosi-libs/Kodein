<img alt="KODEIN" src="https://raw.githubusercontent.com/SalomonBrys/Kodein/master/Kodein-logo.png" width="700">

[![Kotlin 1.1.3-2](https://img.shields.io/badge/Kotlin-1.1-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.salomonbrys.kodein/kodein.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.salomonbrys.kodein%22)
[![Travis](https://img.shields.io/travis/SalomonBrys/Kodein.svg)](https://travis-ci.org/SalomonBrys/Kodein/builds)
[![MIT License](https://img.shields.io/github/license/salomonbrys/kodein.svg)](https://github.com/SalomonBrys/Kodein/blob/master/LICENSE.txt)
[![GitHub issues](https://img.shields.io/github/issues/SalomonBrys/Kodein.svg)](https://github.com/SalomonBrys/Kodein/issues)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-green.svg)](https://kotlinlang.slack.com/messages/kodein/)
[![Donate](https://img.shields.io/badge/Backing-Donate-orange.svg)](https://salomonbrys.github.io/Kodein/#_donate)


KOtlin DEpendency INjection
===========================

Kodein is a very simple and yet very useful dependency retrieval container. it is very easy to use and configure.

Kodein works:

- On the JVM.
- On Android.
- On Javascript (both in the browser and on Node.js).

Kodein allows you to:

- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Easily bind classes or interfaces to their instance or provider
- Easily debug your dependency bindings and recursions

An example is always better than a thousand words:

```kotlin
val kodein = Kodein {
    bind<Dice>() with provider { RandomDice(0, 5) }
    bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
}

class Controller(private kodein: Kodein) {
    private val ds: DataSource = kodein.instance()
}
```

Kodein is a good choice because:

- It is small, fast and optimized (makes extensive use of `inline`)
- It proposes a very simple and readable declarative DSL
- It is not subject to type erasure (as Java is)
- It integrates nicely with Android
- It proposes a very kotlin-esque idiomatic API
- It can be used in plain Java


Read more
---------

[Kodein 5.0 is in Beta!](https://salomonbrys.github.io/Kodein/?5.0)

Kodein version 4 is the current major version available:

- **[Kodein 4 full documentation](https://salomonbrys.github.io/Kodein/)**
- [Kodein 4 API reference](https://github.com/SalomonBrys/Kodein/blob/master/dokka/out/doc/index.md)

If you are currently using a `javax.inject` compatible dependency injection library and whish to migrate to Kodein, [there is a guide for that](https://github.com/SalomonBrys/Kodein/blob/master/MIGRATION-JtoK.adoc).

If you are still using version 3, you can access [version 3 documentation](https://github.com/SalomonBrys/Kodein/blob/master/DOCUMENTATION_V3.adoc).


Support
-------

Support is held in the [Kodein Slack channel](https://kotlinlang.slack.com/messages/kodein/).
You can get an invite to the Kotlin Slack [here](http://slack.kotlinlang.org/).


&nbsp;

Testimonies
-----------

&nbsp;

<img src="https://www.collokia.com/images/collokia-logo.png" height="30" />

> At [Collokia](https://www.collokia.com/) we use Kodein in all of our backend service infrastructure and all modules in those services are loosely coupled through injection with Kodein.
> It allows us to have nice module independence, and to opt-out of injection during testing or build separate modules in support of testing.  
> It is a key component and building block in our architecture.  
> -- [Jayson Minard](https://github.com/apatrida)

&nbsp;

&nbsp;

<img src="https://moovel-group.com/assets/images/sign.png" height="40" />

> At [Moovel Group GmbH](https://www.moovel.com/de/en), we have successfully used the wonderful Kodein library into in [this Android app](https://play.google.com/store/apps/details?id=com.daimler.moovel.android).
> As we improved it, we found Kodein to be much more useful than Dagger2 as it simplified our code throughout.  
> Kodein is in my view, much easier to understand, doesn't have that nasty ceremony, and has really nice debug messages.  
> We are also working now on other projects where we are using Kodein as well.  
> -- [Sorin Albu-Irimies](https://github.com/sorinirimies)

&nbsp;

&nbsp;

<img src="https://insiteapplications.com/static/InSiteLogo.png" width="150" height="35" />

> Kodein has been instrumental in moving our entire production application to Kotlin at [InSite Applications](https://insiteapplications.com). It uses standard Kotlin idioms and features for ultimate clarity and simplicity. It was clear to us from the beginning that Kodein would be our DI solution. Our devs love it so much that they've transitioned to using it in their personal apps, both Java and Kotlin!  
> -- [Eliezer Graber](https://github.com/eygraber)

&nbsp;

&nbsp;

<img src="https://cdn2.hubspot.net/hubfs/2561893/compsoft-creative-logo.jpg" height="35" />

> At [Compsoft Creative](https://www.compsoft.co.uk), Kodein is central our new Kotlin based app architecture, giving us a solid underpinning to all apps we develop and allowing a simple yet powerful way to de-couple our services with a library that is lightweight and perfect for mobile apps.  
> -- [Daniel Payne](https://github.com/CompsoftCreative)

&nbsp;

&nbsp;

<img src="http://imgur.com/Ymo1qoh.jpg" height="60" />

> Kodein is used in [the android app of the OhelShem school](https://github.com/OhelShem/android).  
> -- [Yoav Sternberg](https://github.com/yoavst)&nbsp;

&nbsp;

<img src="https://dental-monitoring.com/wp-content/uploads/2015/02/logo.png" height="60" />

> Kodein was created at [Dental Monitoring](https://dental-monitoring.com/) with the opinion that Dagger2 is way too verbose and complex.
> It is now used in almost all our projects: the server, the internal production software & the Android application.  
> Kodein is very easy to use and set up: it allows our team to easily share code and patterns, as well as quickly bootstrapping new ideas.  
> -- [Salomon Brys](https://github.com/SalomonBrys)

&nbsp;

&nbsp;

If you are using Kodein, please [let me know](mailto:salomon.brys@gmail.com)!
