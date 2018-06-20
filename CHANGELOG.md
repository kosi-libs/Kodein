
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
