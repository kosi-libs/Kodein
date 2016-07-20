# Migration

This document describes the steps needed to migrate from Kodein `2.8` to `3.0`.

Of course, each step is only needed if you use the described feature. Most users will only need the two first steps as
the others are mainly about changes in advanced features.

## Table of contents

  * [Migration](#migration)
    * [Table of contents](#table-of-contents)
    * [Update...](#update)
        * [...Gradle scripts](#gradle-scripts)
        * [...Source imports](#source-imports)
        * [...Currying (*ForFactory)](#currying-forfactory)
        * [...Java code](#java-code)
        * [...Debug](#debug)
        * [...Scopes](#scopes)
        * [...Android's auto activity lifecycle manager](#androids-auto-activity-lifecycle-manager)
        * [...Android's Kodein Aware Application](#androids-kodein-aware-application)
        * [...Lazy Kodein](#lazy-kodein)
    * [Upgrade](#upgrade)
        * [Be aware](#be-aware)
        * [Think global](#think-global)
        * [Use the Android module](#use-the-android-module)


## Update...

#### ...Gradle scripts

First, replace the gradle dependency line with:

```groovy
compile "com.github.salomonbrys.kodein:kodein:3.0.+"
```

If you are using intelliJ, don't forget to update the project's gradle dependencies ;)


#### ...Source imports

`.instance()`, `.provider()` and `.factory()` are now **extension functions**, which means that they need to be
imported.

Simply update the imports in the source files where you are using those functions.


#### ...Currying (*ForFactory)

`instanceForFactory` and `providerForFactory` are removed in favour of `with`.

For example:

```kotlin
val ds: DataSource = kodein.instanceForFactory("user-db")
```

can be replaced by

```kotlin
val ds: DataSource = kodein.with("user-db").instance()
```


#### ...Java code

Java code must manipulate a `TKodein` object (instead of `JKodein`).
It must be passed `kodein.typed` (instead of `kodein.java`).


#### ...Debug

Replace `kodein.registeredBindings` by `kodein.container.bindings`
and `kodein.bindingsDescription` by `kodein.container.bindings.description`.


#### ...Scopes

User defined scopes for scoped and auto-scoped singletons are now objects that implement the `Scope` and `AutoScope`
interfaces. The update is straightforward, read the "[Create your own scopes](https://salomonbrys.github.io/Kodein/#_create_your_own_scopes)" section in the documentation to learn more.


#### ...Android's auto activity lifecycle manager

In your `Application` class, in the `onCreate` method, replace

```
registerActivityLifecycleCallbacks(ActivityScopeLifecycleManager)
```

by

```
registerActivityLifecycleCallbacks(activityScope.lifecycleManager)
```


#### ...Android's Kodein Aware Application

Android specific class `KodeinApplication` has been replaced by the generic `KodeinAware` interface.


#### ...Lazy Kodein

`lazyKodein { /* bindings */ }` is replaced by `Kodein.lazy { /* bindings */ }`.


## Upgrade

#### Be aware

The `KodeinAware`, `KodeinInjected` and `LazyKodeinAware` interfaces allow you to:

- Have retrieval easier to read.
- Retrieve `Class` dependent types, such as loggers.

Read the "[Dependency retrieval](https://salomonbrys.github.io/Kodein/#_dependency_retrieval)" section of the documentation to learn more.


#### Think global

If you wish, you can use the new `kodein-global` module that allows you to have One True Kodein static object. Using
or not using this is a matter of taste and is not recommended nor discouraged.

When using `Kodein.global`, you can use the `KodeinGlobalAware` interface.

Read the "[One True Kodein](https://salomonbrys.github.io/Kodein/#_the_god_complex_one_true_kodein)" section of the documentation to learn more.


#### Use the Android module

The `androidModule` for Android provides a lot of standard bindings that can be used with `withContext`.

Read the "[Android module](https://salomonbrys.github.io/Kodein/#_android_module)" section of the documentation to learn more.
