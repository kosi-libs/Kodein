#### 7.30.0 (TBD)

## What's Changed
* Fixed DSL receiver scope issue (#478): Applied `@DslMarker` to DSL receiver interfaces to prevent accidental calls to outer receiver methods
* Added convenience methods for multi-binding:
    * `add*` methods (set-only): `addSingleton`, `addProvider`, `addInstance`, `addFactory`, `addMultiton`
    * `bind*` methods (set + container): `bindSingleton`, `bindProvider`, `bindInstance`, `bindFactory`, `bindMultiton`
    * `bind*` methods support tag and overrides parameters for individual retrieval from container
* Updated multi-binding documentation with new convenience methods examples

#### 7.29.0 (2025-11-11)

## What's Changed
* Kotlin 2.2.21 + Compose 1.9.3
* feat: add reified helper functions for `NoArgBindingDI`, update tests to use these methods by @romainbsl in https://github.com/kosi-libs/Kodein/pull/492
* feat(#470): add module name verification option to DI by @romainbsl in https://github.com/kosi-libs/Kodein/pull/494
* #467: Improvements to compose injection by @Nek-12 in https://github.com/kosi-libs/Kodein/pull/479
* docs: document reactive compose injection behavior by @Nek-12 in https://github.com/kosi-libs/Kodein/pull/497

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.28.0...v7.29.0

#### 7.28.0 (2025-09-30)

> ℹ️  Due to a wrong manipulation this is the same as 7.27.0

## What's Changed
* Kotlin 2.2.20
* refactor: restructure and modularize Compose runtime to a specific module by @romainbsl in https://github.com/kosi-libs/Kodein/pull/486
* feat(#449): add support for KClass bindings by @romainbsl in https://github.com/kosi-libs/Kodein/pull/487
* Deprecate TornadoFX support and recommend migration to Compose
* chore: migrate workflows to use Central Portal authentication

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.26.0...v7.28.0

#### 7.27.0 (2025-09-30)

## What's Changed
* Kotlin 2.2.20
* refactor: restructure and modularize Compose runtime to a specific module by @romainbsl in https://github.com/kosi-libs/Kodein/pull/486
* feat(#449): add support for KClass bindings by @romainbsl in https://github.com/kosi-libs/Kodein/pull/487
* Deprecate TornadoFX support and recommend migration to Compose
* chore: migrate workflows to use Central Portal authentication

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.26.0...v7.27.0

#### 7.26.1 (2025-05-12)

- fix: downgrade to Kotlin 2.1.20

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.26.0...v7.26.1

#### 7.26.0 (2025-05-03)

## What's Changed
* #469: Add parameterized new support by @Nek-12 in https://github.com/kosi-libs/Kodein/pull/476
* Compose Multiplatform 1.8.0-rc01
* Kotlin 2.1.21-rc02
* Remove Gradle JVM toolchains
## New Contributors
* @Nek-12 made their first contribution in https://github.com/kosi-libs/Kodein/pull/476

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.25.0...v7.26.0

#### 7.25.0 (2025-01-19)

# Compose

- **JS**: Re-enable lost JS target

#### 7.24.1 (2025-01-08)

# Frameworks

- Compose Multiplatform 1.7.3
- Compose  compiler 2.1.0

#### 7.24.0 (2025-01-07)

# Core

- Kotlin 2.1.0
- Gradle 8.11.1
- Replace `ScopeClosable` by Kotlin's `AutoCloseable` #463

# Frameworks

- Migrate to **Ktor 3.+**

# Bug fixes

- Use **kotlinx-atomicfu** to synchronize scopes on each platform #460

#### 7.23.1 (2024-12-02)

chore: revert deprecation on Compose Multiplatform APIs.

#### 7.23.0 (2024-12-01)

- Jetpack Compose : remove deprecated annotations to avoid warning on users

#### 7.22.0 (2024-05-23)

- Kotlin 2.0 !!!

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/v7.21.2...v7.22.0

#### 7.21.2 (2024-01-12)

## Compose Multiplatform
 - `1.6.0-alpha01`
 - Fix Kotlin/Wasm targets

#### 7.21.1 (2023-12-01)

- Kotlin 1.9.21

#### 7.21.0 (2023-11-17)

## Core
- Kotlin `1.9.20`

## Android
- AGP `8.2.0-rc03`

## Compose Multiplatform
 - `1.5.10-dev-wasm02`

**Full Changelog**: https://github.com/kosi-libs/Kodein/compare/7.20.2...7.21.0

#### 7.20.2 (2023-06-26)

### Jetpack Compose

- Fixed #435 : generics were erased while retrieving Android ViewModel on Compose.

#### 7.20.1 (2023-04-04)

⚠️ 7.20.0 Has been badly published, Please use THIS version instead!

- Jetpack Compose
Creation of 1 modules to handle AndroidX ViewModel retrieval, eventually scoped within the NavGraph.

`kodein-di-framework-android-x-compose` : Works with AndroidX ViewModel and Navigation extensions.

#### 7.20.0 (2023-04-03)

⚠️ DO NOT USE : the new modules have been wrongly published.

Use **7.20.1** instead!

- Jetpack Compose
Creation of 2 modules to handle AndroidX ViewModel retrieval, eventually scoped within the NavGraph.

`kodein-di-framework-compose-android`: Works with **AndroidX ViewModel**

`kodein-di-framework-compose-android-navigation` Works with **AndroidX ViewModel** AND **Jetpack Compose Navigation**

#### 7.19.1 (2023-03-31)

- Compose
    - Add a rememberViewModel factory retrieval function

#### 7.19.0 (2023-03-26)

- Core
   - Kotlin 1.8.10
- Compose
   - Multiplatform Compose 1.3.1
   - MacOS targets

#### 7.18.0 (2023-01-13)

Enable Compose Multiplatform `1.3.0-rc2` with Kotlin `1.8.0` compatibility.

#### 7.17.0 (2022-12-29)

- **Kotlin** 1.8.0
- **Ktor** 2.2.1
- **JS**: Since Legacy backend as been deprecated, Kodein will only support IR.

⚠️ Compose capabilities are disable as the compiler plugin has not been released yet.
We should be able to re-enable it in 7.18.0 as soon as a new version of compiler plugin will be released.

#### 7.16.0 (2022-11-14)

- Compose 1.2.1 with Kotlin/JS
- New multi-binding API (see documentation http://kosi-libs.org/kodein/7.16/core/multi-binding.html)
```kotlin
val di = DI {
    bindSet<Configuration> {
        add { provider { FooConfiguration() } }
        bind { singleton { BarConfiguration() } }
    }
}
```

#### 7.15.1 (2022-11-04)

Enable Compose Multiplatform 1.2.0.

⚠️ Compose Multiplatform 1.2.0 is not compatible with **Kotlin/JS 1.7.20**, but you can use it with **Kotlin 1.7.10.**

#### 7.15.0 (2022-09-29)

- Kotlin 1.7.20

⚠️ Compose capabilities are disable as the compiler plugin has not been released yet.
We should be able to re-enable it in 7.15.1 as soon as a new version of compiler plugin will be released.

#### 7.14.0 (2022-07-18)

- Gradle 7.5
- Kotlin 1.7.10
- Enable compatibility with non-hierarchical projects

#### 7.13.1 (2022-07-15)

Compose Multiplatform `1.2.0-alpha01-dev745` with **Kotlin 1.7** compatibility

#### 7.13.0 (2022-06-21)

CORE:
    - Kotlin 1.7.0
    - Deprecation cycle

⚠️ **Compose** capabilities are disable as the compiler plugin has not been released yet.
We will re-enable it in 7.13.1 as soon as a new version of compiler plugin will be released.

#### 7.12.0 (2022-05-31)

- CORE
    - Kotlin `1.6.21`
    - Constructor reference based binding (#408 thanks to @rocketraman)
    ```kotlin
    DI {
        bindSingleton { new(::PersonService) }
    }
    ```

    - Delegate binding (#406)
    ```kotlin
    DI {
        bindSingleton { Cls() }
        delegate<Cls>().to<Cls>()
    }
    ```
    - Create modules with delegate (#393)
    ```kotlin
    val myModule by Module { // implicitly named "myModule"
        bind { singleton { Cls() } }
    }
    ```

- KTOR
    - Moved to `2.0`: This has no impact for you, all the breaking changes are internals.

- COMPOSE
    - Upgrade to `1.2.0-alpha01-dev683`

- EXTENSION:
    - Improve `ConfigurableDI` (#395 & #396)

- Documentation fixes

#### 7.11.0 (2022-02-18)

  - CORE
    * Documentation improvements (thanks to the contributors!).  
    * Deprecation cycle
  - COMPOSE
    * JB Compose 1.1.0 Alpha5
    * Introduce `rememberDI` Composable function
    * Fix `rememberX` functions` behavior
    * Update documentation
    
#### 7.10.0 (29-10-2021)

  - COMPOSE
      * JB Compose beta5
      * Enable Web target

#### 7.9.0 (24-10-2021)

  - CORE
      * Gradle 7.2
      * AGP 7
      * Kotlin 1.5.31
      * Apple Silicon support
  - COMPOSE
      * JB Compose beta1
      * Android : remember ViewModel helper

#### 7.8.0 (12-08-2021)

  - CORE
    * Kotlin 1.5.21
    * Documentation
  
  - ANDROID
    - 2 new modules have been created to support ViewModel retrieval with ease (thanks to @carltonwhitehead for his contribution).
      - kodein-di-framework-android-x-viewmodel : Injection and retrieval of plain View Models with by viewModel() delegate.
      - kodein-di-framework-android-x-viewmodel-savedstate : Injection and retrieval of View Models with SavedStateHandle with by viewModelWithSavedStateHandle() delegate.
      
  - COMPOSE: `kodein-di-framework-compose` module is now align with the stable version of Compose compiler (compatible with Kotlin 1.5.21) with improvements

#### 7.6.0 (21-05-2021)

  - CORE:
    * Kotlin 1.5
    * Kodein-Type 1.7.1
      * Thanks to @eygraber for the heavy investigation #309
  - Documentation

#### 7.5.0 (30-03-2021)

  - CORE
    * Direct binding by adding `bind(tag: Any?, overrides: Boolean?, createBinding: () -> DIBinding)`
      * `bind { singleton { Person("Romain") } }` is equivalent to `bind() from singleton { Person("Romain") }`
      * `bind<IPerson> { singleton { Person("Romain") } }` is equivalent to `bind<IPerson>() with singleton { Person("Romain") }`
      * `bind { scoped(SessionScope).singleton { Person("Romain") } }` is equivalent to `bind() with scoped(SessionScope).singleton { Person("Romain") }`
      * etc.
    * Simplified binding APIs
      * `bindFactory` / `bindProvider` / `bindSingleton` / `bindMultiton` / `bindInstance` / `bindConstant`
      * `bindSingleton { Person("Romain") }` is equivalent to `bind() from singleton { Person("Romain") }`  
      * `bindSingleton<IPerson> { Person("Romain") }` is equivalent to `bind<IPerson>() with singleton { Person("Romain") }`
      * etc.
    * Documentation
  - FRAMEWORK
    * Adding Compose support for both Android (Jetpack) and Desktop (JetBrains).

#### 7.4.0 (27-02-2021)

  - CORE
    * Kotlin 1.4.31
    * Maven Central publication

#### 7.3.1 (08-02-2021)

  - CORE
    * Enable new JS IR backend

#### 7.3.0 (05-02-2021)

  - CORE
    * Kotlin 1.4.30
    * Documentation

#### 7.2.0 (28-12-2020)

  - CORE
    * Kotlin 1.4.20
    * Fix memory leak with DI context
    * Context finder and translator can now access DI container's bindings
    * Kodein-Type 1.4 + other dependencies

#### 7.1.0 (18-09-2020)

  - DOCUMENTATION
    * New documentation format https://docs.kodein.org/kodein-di/
        * Split full documentation into chapters

  - CORE
    * Kotlin 1.4.0 
    * Explicit public API mode
    * Fixed possible memory leak with DI context
    * Deprecation cycle
    
  - BUILD
    * Gradle 6.5.1   
    * New Internal Gradle Plugin   
    * New snapshot workflow

#### 7.0.0 (22-05-2020)

  - DOCUMENTATION
    * Updated http://kodein.org/Kodein-DI/
        * New type system
        * Module refactoring
        * Code refactoring
    * Migration from version 6 to 7: http://kodein.org/Kodein-DI/?7.0/migration-6to7

  - CORE
    * Modules refactoring: `org.kodein.di.generic-jvm` / `org.kodein.di.erased` combined into `org.kodein.di` 
    * New [type system](https://github.com/Kodein-Framework/Kodein-Type) with `typeOf` to handle generics, for non JVM targets only.
    * Package deprecation: `org.kodein.di.generic`, `org.kodein.di.erased`.
    * Internals: non nullable context types / test re-organization / fixes
    * Deprecation cycle
    
  - FRAMEWORKS
    * Android / Ktor / TornadoFX : Migration from 6 to 7 
    
  - DEMOS
    * Migrating the [Kodein-DI samples]( https://github.com/Kodein-Framework/Kodein-Samples) projects from 6 to 7

  - BUILD
    * Gradle 6.3   
    * New Internal Gradle Plugin   
    * Github Actions   

#### 6.5.5 (15-04-2020)

- CORE
  * Kotlin `1.3.72`
   
#### 6.5.4 (23-03-2020)

- CORE
  * Kotlin `1.3.71`
  
#### 6.5.3 (04-03-2020)

- CORE
  * Kotlin `1.3.70`
  
#### 6.5.2 (19-02-2020)

- CORE
  * tvOS and watchOS compatibility    

#### 6.5.1 (09-12-2019)

- CORE
  * Fix build issue for Kotlin/Native projects    
  
#### 6.5.0 (27-11-2019)

- CORE
  * Depreciate multi-args factories, we now recommend to use data classes instead   
  * Kotlin `1.3.60`
  * Documentation
  
#### 6.4.1 (07-10-2019)

- CORE
  * Hotfix on base class injection, failing on automatic context (#254)   

#### 6.4.0 (19-09-2019)

- CORE
  * Allowing to use `importOnce` over extended modules
  * Kotlin `1.3.50`
- JVM
  * Set the `JDK8` as default target
- KTOR
  * Route.controller extension functions to be able to install KodeinController routes inside the Ktor routing system.
  * KodeinControllerFeature depreciation as the new extensions will better cover the use case
- DEMOS
  * The samples projects have been moved. They are in a separate repo
    * https://github.com/Kodein-Framework/Kodein-Samples
- CI/CD
  * Automate builds on Travis CI
- MISC
  * Move to a more traditional branching model with:
    * `master` as default 
    * `stable` for release 
    * `X.X` for versioning

#### 6.3.4 (26-07-2019)

- BUILD
  * Fix build compatiblity for JVM targets   

#### 6.3.3 (11-07-2019)

- CORE
  * Hotfix on false positive bindings (#216)   

#### 6.3.2 (27-06-2019)

- JVM
  * As we build Kodein with `JDK12`, the JVM librairies no longer throw exception when used with **Gradle > 5.2**, 
  by bringing target compatibility on `JDK8`.  

#### 6.3.1 (25-06-2019)

- ANDROID
  * the function `closestKodein` aliases `kodein` for import conflicts, in modules `kodein-di-framework-android-support` and `kodein-di-framework-android-x`.


#### 6.3.0 (20-06-2019)

- TORNADOFX
  * New framework module for TornadoFx integration thanks to the huge work of Romain Boisselle! See [documentation](https://kodein.org/Kodein-DI/?6.3/tornadofx).

- CORE
  * `importAll` allows to import multiple modules at once
  * Kotlin `1.3.40`
  * Named retrieval allows `val name: String by named.instance()` and `val name: String by constant()`
  * If `fullDescriptionOnError` is true, all Kodein errors will include full type names.
  * The `externalSource` var is now deprecated in favour of the `externalSources` mutable list.

- NATIVE
  * Updated to Gradle Module schema 1.0. Gradle 5.3.0 minimum is required!

- ANDROID
  * the function `closestKodein` aliases `kodein` for import conflicts.

- KTOR
  * the function `closestKodein` aliases `kodein` for import conflicts.


#### 6.2.1 (24-05-2019)

- ANDROID
  * Fixed a regression to ActivityRetainedScope


#### 6.2.0 (18-04-2019)

- CORE
  * Kotlin 1.3.30
  * Fixed a bug in the JVM specific code that prevented a generic interface from being retrieved when a concrete class was bound.
  * `bind() from Binding<Unit>` is now forbidden. Binding specifically the Unit type with `bind<Unit>() with Binding<Unit>` is allowed (although I cannot see why someone would use it!).

- KTOR
  * New framework module for Ktor integration thanks to the huge work of Romain Boisselle! See [documentation](https://kodein.org/Kodein-DI/?6.2/ktor).

#### 6.1.0 (06-02-2019)

- CORE
  * Kotlin 1.3.20
  * Fixed a bug that made a cached "hit" in the container loose its translator for future access (Thanks to Charles Julian Knight).
  * Kodein.Lazy supports both `by` and `=`, which makes it easier to use (most `*Lazy` classes in Kotlin are supposed to be used with `by`).
  * Support for lazy contexts via `on { context }.instance()` or via `override val kodeinContext = kcontext { context }`.

- MULTIPLATFORM
  * Gradle 4.10 & Gradle Metadata 0.4 by default.
  * LGM versions are dropped since they are not needed anymore.

- ANDROID
  * Fixed a bug that may duplicate the underlying fragment when using `retainedKodein` (Thanks to Charles Julian Knight).
  * `closestKodein` is renamed `kodein`, which is easier to read & understand, and do not require users to understand multi-layering.
  * Introducing `subKodein` and `retainedSubKodein` that makes multi-layering easier.
  * Official support for Android's `ViewModel` classe with it's own `kodein` accessor & documentation tips.
  * `androidModule` is renamed to `androidCoreModule` to emphasize the fact that one should rather use `androidXModule` or `androidSupportModule`.
  * Android specific context translators can be accessed outside of the android modules.

- INTERNAL
  * `KodeinContext` is now an interface.


#### 6.0.1 (30-11-2018)

- CORE
  * Fixed context translator & finder "collision".
  * Improved multi-theading.
  
- ANDROID
  * Version bump.

- DEPLOYMENT
  * Fixed POM dependencies & module file uploads.

#### 6.0.0 (09-11-2018)

- CORE
  * Kotlin 1.3.0
  * Simplified `Scope` interface
  * Introducing context translators & context finders
  * Introducing sub scopes

- NATIVE
  * New multiplatform gradle plugin


#### 5.3.0 (03-10-2018)

- CORE
  * Introducing non-synced singletons & multitons (can be used with the `sync = false` parameter).

- NATIVE
  * New `kotlin-platform-native` plugin

- ANDROID
  * Fixed a memory leak in `AndroidLifecycleScope` (Thanks to Eliezer Graber).
  * Fixed a crash when binding arrays.

- INTERNALS
  * Builders are interfaces


#### 5.2.0 (20-04-2018)

  - CORE
    * Named Modules cannot be imported more than once.
    * Introducing `importOnce` that allows to import a named module if it has not already been imported:
      [see documentation](http://kodein.org/Kodein-DI/?5.2/core#module-uniqueness).
    * Introducing `newScopeRegistry` that creates either a single or a multime item registry.

 - ANDROID
   * Android support has been split between `kodein-di-framework-android-core`, `kodein-di-framework-android-support` (for the Android Support library) and `kodein-di-framework-android-x` (for the AndroidX library): 
     [see documentation](http://kodein.org/Kodein-DI/?5.2/android#install).

#### 5.1.1 (20-04-2018)

  - ANDROID
    * Fixed `ActivityRetainedScope` NullPointerException regression.


#### 5.1.0 (16-04-2018)

  - CORE
    * BREAKING CHANGE: updated the `ScopeRegistry` and `Scope` interfaces to get an `A` generic argument that allows to create scopes specialized for a type of factory argument.
    * Introduced the `ScopeCloseable` interface that allows singletons / multitons objetcs to be closed when they are removed from the scope: [see documentation](http://kodein.org/Kodein-DI/?5.1/core#scope-closeable).
    * The `SingleItemScopeRegistry` class now allows for key change, which closes the previous value and keeps the new.
      This allows for `scoped(SingleItemScopeRegistry<Any?>()).multiton`:
      [see documentation](http://kodein.org/Kodein-DI/?5.1/core#scope-registry).
    * Unnamed module creation deprecated (in favour of named modules).
  
  - ANDROID
    * `activityRetainedScope` deprecated in favour of `ActivityRetainedScope`.
    * `ActivityRetainedScope` is compatible with `ScopeCloseable`.
    * `androidScope` deprecated in favour of `AndroidComponentsWeakScope`.
    * Introducing `AndroidLifecycleScope`: a scope that uses LifecycleOwner components to close properly close instances.


#### 5.0.1 (16-04-2018)

  - FRAMEWORK
    * The Kodein-DI library is officially part of the in-progress Kodein Framework!

  - GRADLE
    * Project is entirely configured with Gradle Kotlin DSL
    * Project relies configuration relies heavily on `kodein-internal-gradle-plugin` which abstracts the configuration of all Kodein Framework components.
  
  - CORE
    * Added `factoryX` functions to enable to directly retrieve a multi-argument function `(A1, A2) -> T` when using multi-argument bindings.
   
   - ANDROID
    * Corrected a stack overflow error when using generic types on SDK 19 and lower
   
  - NATIVE
    * Using new native distribution model to allow gradle dependency retrieval
    

#### 5.0.0 (10-04-2018)

  - DOCUMENTATION
    * Fragmented documentation: http://kodein.org/Kodein-DI/
    * Getting started: http://kodein.org/Kodein-DI/?5.0/getting-started
    * Migration from version 4 to 5: http://kodein.org/Kodein-DI/?5.0/migration-4to5

  - CORE
    * Package change: `org.kodein.di`, `org.kodein.di.generic`, `org.kodein.di.erased`.
    * Everything is lazy by default.
    * Distribution via Bintray's JCenter.
    * Compatible with Kotlin/Native (Huge thanks to Nikolay Igotti and his amazing Kotlin/Native team!).
    * Support for subtype bindings: [see documentation](http://kodein.org/Kodein-DI/?5.0/core#_subtypes_bindings).
    * Ability to retrieve `allInstances`, `allProviders` or `allFactories` that match subtypes of a given type.
    * Support for multi-argument factories & multiton.
    * Changed the `extend` semantic to manage copy of state-holding bindings. [see documentation](http://kodein.org/Kodein-DI/?5.0/core#_overridden_access_from_parent).
    * Complete rewrite of custom scopes.
    * Better recursion error messages.
    * Better currying syntax: `kodein.instance(arg = whatever)`.
    * Injector has been replaced with `KodeinTrigger` and `LateInitKodein`, each taking a part of the responsibility.
    * Introducing `LateInitKodein`.
    * `scopeSingleton` and `refSingleton` are now options of `singleton`. Same goes for `multiton`.
    * Support for non state-holding bindings to access the `receiver`.
    * Support for an external source that will be queried when Kodein has no answer.
    
  - ANDROID
    * Full rewrite of the android components, effectively removing them, replacing them with a much lighter system. [See documentation](http://kodein.org/Kodein-DI/?5.0/android)
  
  - INTERNALS
    * Full rewrite of the retrieval engine, introducing `KodeinTree` to select the matching binding according to a query.

#### 4.1.0 (28-07-2017)

 - CORE
   * Kotlin 1.1.3
   * Introducing `CompositeTypeToken`, `erasedComp1`, `erasedComp2` and `erasedComp3` to describe generic types in an erased-only environment.
   * Introducing the `SetBinding` binding for Set multi-binding.

 - ANDROID
   * `KodeinSharedPreferencesInfo` is now outside of autoAndroidModule.
   * Added `KodeinPreferenceFragment`.

 - INTERNALS
   * `JxInjector` now delegates to the internal `JxInjectorContainer`.
   * Refactored `TypeToken`

#### 4.0.0 (08-06-2017)

 - FEATURES
   * Kotlin 1.1.2-4.
   * The `generic` function is now 40% faster.
   
 - BUG FIX
   * Kodein JS now intercept type reflection failure (which happens when reflecting on primitive types on older versions of Kotlin).
 
 - REMOVAL
   * Removed the `sequence` coroutine binder (too experimental).

#### 4.0.0-beta2 (24-04-2017)

 - FEATURES
   * Kotlin 1.1.0.
   * Javascript modules (`kodein-js` & `kodein-js-conf`).
     Uses the erased methods as JS does not support generic type reflection.
   * The Kodein binding DSL is now protected with `@DslMarker` to prevent weird things from happenning.
   * New binding factory: `sequence` which uses coroutines to bind a sequence.
   
- BREAKING CHANGES
   * Removed all `erased*` and `generic*` methods.
     Each core method now takes type parameters that are obtained with either `generic()` or `erased()`.
   * Functions and classes that were part of the internal system, but declared public (because inline function references) are now truly internal.
   * Removed all deprecated API: new major version means clean slate.

- STRUCTURE CHANGES
   * Every type is now represented with a `TypeToken<T>` instead of a `Type`.

- BUG FIX
  * issue #61: FullTypeStringer failed on a type with no package.

### 3.4.1 (24-04-2017)

- BUG FIX
  * issue #61: FullTypeStringer failed on a type with no package.

### 3.4.0 (18-04-2017)

 - FEATURES
   * New module: `kodein-jxinject` which allows to gradually move from a `javax.inject.*` java injector.
   * New sugar syntax `kodein.newInstance {}`.

### 3.3.0 (24-03-2017)

 - ANDROID FIXES
   * Initialize the injector before super.onCreate in components (Thanks to Eliezer Graber). This **may** be a breaking change.
   * In a fragment, you can now retreive a Layout Inflater either via a service or using the tag ACTIVITY_LAYOUT_INFLATER (Thanks to Eliezer Graber).
   * FragmentInjector injects from parent fragment when present (Thanks to Corey Downing).
   * Enforce that an KodeinFragment must be used inside a KodeinActivity (Thanks to Eliezer Graber & Corey Downing).

 - FEATURES
   * Detect recursive initialization in configurable module (Thanks to Francesco Vasco).
   * Added `refSingleton` which enables a singleton managed by a reference object (suggested by Francesco Vasco)..
   * Added `multiton` which is like a `singleton` but whose uniqueness is defined by a parameter (suggested by Francesco Vasco).
  
 - DEPRECATION
   * `threadSingleton {}` is deprecated in favour of `refSingleton(threadLocal) {}`. 

 - STRUCTURE CHANGES
   * Better synchronization.
   * Removed `CFactory` and `CProvider`.
   * In `Factory`, `factoryName` is now a function, which forces computation only when needed.


### 3.2.0 (26-01-2017)
No changes from `3.2.0-beta3`


### 3.2.0-beta3 (29-12-2016)

 - FEATURES
   * You can use `overriddenInstance` in an overriding binding to access the instance retrieved by the overridden binding.
   * For Android, added the `autoAndroidModule`: a module that allows for automatic retrieval of Android services, without providing a `Context` (Thanks to Eliezer Graber).
   * For Android, added Android component classes that makes it easier to bootstrap Kodein with Android (Thanks to Eliezer Graber).
   * For Android, all services accessible via `Context.*_SERVICE` are now available in the Android modules (up to Android `N_MR2`).

 - STRUCTURE CHANGES
   * `Curried*` extension methods (introduced in beta2) don't need the `A` (argument type) generic parameter.

### 3.2.0-beta2 (28-10-2016)

 - FEATURES
   * Every function that do generic type reflection is renamed `generic*` and has an `erased*` counterpart.
   * All functions are now inside the `kodein-core` module. The `kodein` module defines extension functions that alias
     to the  "generic*" functions by default.
   * The `kodein-erased` module is the same as the `kodein` module, but with functions that alias to the `erased*`
     functions.

 - DEPRECATION
   * Removed methods that were deprecated in 3.1.0
   * For Android, \[scope\]ScopedSingleton methods are deprecated (for example, `contextScopedSingleton {}` is
     deprecated in favour of `scopedSingleton(androidContextScope) {}`)
   * `typeToken` has been renamed to `generic`. The `typeToken` function still exists but is deprecated.

 - STRUCTURE CHANGES
   * The `bind` methods and their `with` associates are now extension functions and need to be imported.
   * Curried retrieval methods such as the `instance` in `with(whatever).instance()` are now extension functions and
     need to be imported.

### 3.1.0 (22-09-2016)

 - FEATURES
   * Android: Added more scopes for `Fragment`, `Service` and `BoradcastReceiver` (Thanks to Eliezer Graber).
   * `ConfigurableKodein` objects that are declared `mutable` can now be mutated with `addConfig`, `addImport` and
     `addExtend`.
   * Factories that take a class as parameter now work with their child classes without upcast (eg: you can retrieve
     with `with(obj).instance()` instead of `with(obj as Type).instance()`)
 
 - FIX
   * Android module: The `KeyguardManager` and `WallpaperService` are now properly injected. (Thanks to Eliezer Graber).
 
 - DEPRECATION
   * In `ConfigurableKodein`, all `mutate*` methods are deprecated (in favour of their regular counterparts).
   
 - INTERNAL
   * Better type display (Thanks to Alexander Udalov).
   * The Android demo project is now a module (Thanks to Eliezer Graber).

### 3.0.0 (21-07-2016)

 - FEATURES
   * Introducing `ConfigurableKodein` in the `kodein-conf` module that can be configured then used for retrieval.
   * A `ConfigurableKodein` object can be mutable (if its `mutable` property is true). Please be careful with this!
   * The Android module contains a factory for default `SharedPreferences`.

 - BETA FEATURE CHANGES
   * `Kodein.global` is now in the `kodein-conf` module.

### 3.0.0-beta6 (19-07-2016)

 - FEATURES
   * Lazy retrieval with `kodein.lazy.*`.
   * Currying with lazy properties: `with().lazy.*`.
   * You can add config other then imports or extend on global Kodein with `Kodein.global.addConfig`.
   * `Kodein.Lazy` accepts a `allowSilentOverride` parameter.

 - BREAKING CHANGES
   * `lazyKodein` is renamed `Kodein.Lazy`.
   

### 3.0.0-beta5 (12-07-2016)

 - FEATURES
   * Kodein's source code & API is now fully documented!
   * You can now bind a `Kodein.Bind` directly with `container.bind(bind)`.
   * `lazyKodein {...}` now returns a `LazyKodein` object, which can be used either as a lazy property or to inject lazy
     properties.
   * You can use `with(() -> A)` instead of `with(A)` for currying if you don't have access yet to the argument.

 - BREAKING CHANGES
   * `instanceForFactory` and friends, which were not documented, are removed.
   * `.toProvider(A)` is replaced by `.toProvider(() -> A)`. You can easily call it `.toProvider { arg }`. This is
     because, sometimes, you don't have access to the parameter when you are declaring the injection (for example, in
     Android, a Fragment don't have access to a context Activity before being attached).
   * Every factory currying should only use `with`.
   * Constant bindings are now resolved using compile time type. To use the real type of the object (not really
     recommended), use `typed.constant("tag").with(value, value.javaClass)`.

 - BETA FEATURE CHANGES
   * `instanceForClass` and friends are replaced by `withClass` and friends.
   * `T.withClass(Kodein)` is replaced by `Kodein.withClassOf(T)`.
   * Scopes do not get the key to retrieve the registry.
   * Android's `T.withContext` is replaced by `withContext(T)`. This is because of [a Kotlin's bug](https://youtrack.jetbrains.com/issue/KT-9630).

 - INTERNAL
   * `KodeinParameterizedType` is renamed `KodeinWrappedType`.
   * GenericArrayTypes are now also protected by `KodeinWrappedType`.
   * Better `hashCode` and `equals` in KodeinWrappedType.
   * `TKodein` is now a class (not an interface), which allows the use of `@JvmOverloads`.


### 3.0.0-beta4 (03-07-2016)

 - FEATURES
   * `*OrNull` functions everywhere

 - BREAKING CHANGES
   * Android's `KodeinApplication` is removed in favor of `KodeinAware`.

 - BETA FEATURE CHANGES
   * In `Kodein.Builder`, separation of typed & container API Inside `Kodein.Builder`. `bind(type) with ...` is move to
     `typed.bind(type) with ...`.


### 3.0.0-beta3 (03-07-2016)

 - FEATURES
   * `kodein.container.bindings.description` now uses simple type names, which makes it easier to read. You can use
     `kodein.container.bindings.fullDescription` to show full type names.
   * Both descriptions now print type names in a "kotlin-esque" way. Because Kodein does not depends on
     `kotlin-reflect`, it uses java `Type` objects that do not contains nullability informations. As such, the type
     display does not include nullability. Still, it's easier to read.
   * Kotlin `1.0.3`

 - BREAKING CHANGES
   * Scopes are no more (weird) functions but object implementing the `Scope` or `AutoScope` interfaces.
   * Android's `ActivityScopeLifecycleManager` is now `activityScope.lifecycleManager`.

 - BETA FEATURE CHANGES
   * `typeToken` won't throw an exception when used with a `TypeVariable` type argument, however, the same exception
     will still be thrown at binding, whether or not using the inline function, `typeToken` or a simple type. In other
     words, if you try to bind any type that contains a `TypeVariable` type argument, `Kodein` will throw an exception.

 - INTERNAL
   * Android's `activityScope` is now an object.


### 3.0.0-beta2 (30-06-2016)

 - FEATURES
   * `KodeinInjected` can use `kodein()` instead of `injector.kodein()`.
   * Android `instanceForContext` that works exactly like `instanceForClass`, but for a `Context`.
   * Android module, wisely named `androidModule` that provides a lot of factories with a `Context` arguments (see
     `AndroidModule.kt`).

 - BREAKING CHANGES
   * `typeToken` (and therefore `TypeReference`) will throw an exception when build with a `TypeVariable` type argument.
     This is to prevent "accidental" binding of a type with TypeVariable which will be later impossible to retrieve.

 - BETA FEATURE CHANGES
   * `instanceForClass` uses Java `Class<*>` and `instanceForKClass` uses Kotlin `KClass<*>`.


### 3.0.0-beta1 (29-06-2016)

 - BREAKING CHANGES
   * `JKodein` is replaced by `TKodein` which is meant to use by both Kotlin & Java. `TKodein` allows you
     to access a type consistent API without `inline` methods. Each method can be used with `Type`, `TypeToken`, or
     `Class`. To access a `TKodein`, simply use `kodein.typed`.
   * The `typeToken` function now returns a `TypeToken` interface. This allows to keep type consistency &
   checking while `Type` objects. If you were using `val t = typeToken<T>()` in Kodein 2, the replacement is simply
   `val t = typeToken<T>().type`.
   * `kodein.bindingsDescription` becomes `kodein.container.bindings.description`.
   * `kodein.registeredBindings` becomes `kodein.container.bindings`.
   * All `inline` functions that use `reified` to get the type are now **extension functions**, which means that they
      need to be imported! Your code will be all red until you resolve those import. Don't panic! The API itself has
      not changed, just its imports.

 - FEATURES
   * Introducing `kodein.container` which enables you to query the Kodein container directly with `Kodein.Key` or
     `Kodein.Bind` objects.
   * `kodein.container.bindings` now gives a `Map<Kodein.Key, Factory<*, *>>`. Which means that you can do all sorts of
     introspection and debug (like know which types are bounds to which factories, etc.). To help you with those tasks,
     several extension functions are proposed to this `Map` (in the file `bindings.kt`).
   * Better bindings description: now displays the bound type and the created (real) type.
   * Introducing the `KodeinAware` and `KodeinInjected` interfaces. If a class implements one of those interfaces, the
     injection feels "native": `instance()` instead of `kodein.instance()`.
   * Introducing `instanceForClass()` and `providerForClass()`: those methods can be passed a `Kodein` or
     `KodeinInjector` object to inject an instance by using a factory that takes a `KClass<*>` as parameter. Those
     functions can be used without parameter on a `KodeinAware` or `KodeinInjected` class (which is where they reach
     their full potential!).
   * Introducing `Kodein.global` which is a global Kodein instance for all. It is on its module and is not proposed by
     default. To use it, you must declare the dependency `org.kodein:kodein-global:3.0.+`. You can
     add modules to it with `Kodein.global.addImport(module)`. After that, you can use `Kodein.global` as a regular
     Kodein object. Be aware that once you have injected / retrieved the first value with `Kodein.global`, then adding
     modules to it will throw an exception.
   * When using `Kodein.global`, you can use the `KodeinGlobalAware` interface, which enables you all the goodness of
     `KodeinAware`, but using `Kodein.global`, and without any config.
   * Kotlin `1.0.2-1`

 - INTERNAL
   * All providers & factories are represented by their classes.
   * `Kodein`, `TKodein` and `KodeinContainer` are now very simple interfaces, which enables easy wrapping.


### 2.8.0 (14-06-2016)

 * Eager singletons: ask kodein to instanciate the singleton object as soon as kodein is ready.
 * `KodeinInjector` is now thread safe.
 * The `KodeinInjector.onInjected` callback is directly called if the injector has already been injected.
 * Gradle `2.13`


### 2.7.1 (02-06-2016)

 * Overriding exceptions now print the affected key
 * New syntax for binding without specifying the type: `bind() from scope`


### 2.7.0 (23-05-2016)

 * Kotlin `1.0.2`.
 * Overriding restrictions and policies (overrides must now be explicit).
 * Scoped singleton system.
 * Android Activity scope binding syntax.
 *`kodein.with` that makes it easier to deal with factory bindings (better than `toProvider` and `toInstance`).
 * Factories arguments can be nullable.


### 2.6.0 (19-02-2016)

 * Kotlin `1.0.0`.


### 2.5.0 (04-02-2016)

 * Kotlin `1.0.0-rc-1036`.


### 2.4.1 (11-01-2016)

 * Kotlin `1.0.0-beta-4584`
 * `KodeinInjector` can inject the `Kodein` object.
 * `KodeinInjector` has a `onInjected` callback.
 * Android: `lazyKodeinFromApp` and `appKodein` support for `View`, `AbstractThreadedSyncAdapter` and `Loader`.


### 2.3.1 (06-11-2015)

 * Container extension: allows to `import` a kodein object's binding into another kodein object.


### 2.3.0 (02-11-2015)

 * Kotlin `1.0.0-beta-1038`
