<img alt="KODEIN-DI" src="https://raw.githubusercontent.com/Kodein-Framework/Kodein-DI/6.1/Kodein-DI-logo.png" width="700">

[![Kotlin 1.3.72](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg?style=flat&logo=kotlin)](http://kotlinlang.org)
[![JCenter](https://api.bintray.com/packages/kodein-framework/Kodein-DI/Kodein-DI-Core/images/download.svg)](https://bintray.com/kodein-framework/Kodein-DI)
![Github Actions](https://github.com/Kodein-Framework/Kodein-DI/workflows/build%20and%20publish%20a%20snapshot/badge.svg)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/Kodein-Framework/Kodein-DI/blob/master/LICENSE.txt)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-green.svg?style=flat&logo=slack)](https://kotlinlang.slack.com/messages/kodein/)


KOtlin DEpendency INjection
===========================

**_Kodein-DI_** is a very simple and yet very useful dependency retrieval container. it is very easy to use and configure.

**_Kodein-DI_** works:

- On the JVM.
- On Android.
- On Javascript (both in the browser and on Node.js).
- On Native platforms (such as iOS).

**_Kodein-DI_** allows you to:

- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Easily bind classes or interfaces to their instance or provider
- Easily debug your dependency bindings and recursions

**_Kodein-DI_** provides extensions to be integrable into:

- [Android](https://developer.android.com/)
- [Ktor](https://ktor.io/)
- [TornadoFX](https://tornadofx.io/)

An example is always better than a thousand words:

```kotlin
val di = DI {
    bind<Dice>() with provider { RandomDice(0, 5) }
    bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
}

class Controller(private di: DI) {
    private val ds: DataSource by di.instance()
}
```

**_Kodein-DI_** is a good choice because:

- It proposes a very simple and readable declarative DSL
- It is not subject to type erasure (as Java is)
- It integrates nicely with Android
- It proposes a very kotlin-esque idiomatic API
- It is fast and optimized (makes extensive use of `inline`)
- It can be used in plain Java

### Looking for **Kodein-DI 7.0** migration guide?
> Folow this us [here](https://kodein.org/Kodein-DI/?7.0/migration-6to7).

Kotlin & JVM compatibility
---------

|Kodein-DI|Kotlin|JDK
|:---:|:---:|:---:|
|7.0+|1.3.72|min 1.8
|6.5.5|1.3.72|min 1.8
|6.5.4|1.3.71|min 1.8
|6.5.3|1.3.70|min 1.8
|6.5.0|1.3.61|min 1.8
|6.4.1|1.3.50|min 1.8
|6.3+|1.3.40|min 1.8
|6.2+|1.3.30|1.6
|6.1+|1.3.20|1.6
|6.0+|1.3.0|1.6
|5.0+|1.2.30|1.6
|4.1+|1.1.3|1.6
|4.0.0-beta2|1.1.0|1.6

Demo Projects
---------
You can find samples for MPP project here https://github.com/Kodein-Framework/Kodein-Samples 

Read more
---------

**_Kodein-DI_** 7+ is the current major version, but documentation is available for previous versions.

**[Kodein-DI documentation](http://kodein.org/Kodein-DI/)**


Support
-------

Support is held in the [Kodein Slack channel](https://kotlinlang.slack.com/messages/kodein/)
(you can get an invite to the Kotlin Slack [here](http://slack.kotlinlang.org/)).


Future
------

The following frameworks will receive special love from Kodein:

- Android
- Cocoa-Touch (iOS), **// TODO**
- Ktor
- TornadoFX


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

If you are using Kodein-DI, please [let us know](mailto:contact@kodein.net)!
