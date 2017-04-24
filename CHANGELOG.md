
### 3.4.1 (24-04-2017)

 - BUG FIXES
   * Crash in `fullDescription` when displaying the name of a package-less class.

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
   * Every function that do generic type reflexivity is renamed `generic*` and has an `erased*` counterpart.
   * All functions are now inside the `kodein-core` module. The `kodein` module defines extension functions that alias
     to the  "generic*" functions by default.
   * The `kodein-erased` module is the same as the `kodein` module, but with functions that alias to the `erased*`
     functions.

 - DEPRECATION
   * Removed methods that were deprecated in 3.1.0
   * For Android, \[scope\]ScopedSingleton methods are deprecated (for example, `contextScopedSingleton {}` is
     deprecated in favour of `scopedSingleton(androidContextScope) {}`)
   * `typeToken` has been renamed to `genericToken`. The `typeToken` function still exists but is deprecated.

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
   * Kodein's source code & API is now [fully documented](https://salomonbrys.github.io/Kodein/kodein-dokka/kodein/com.github.salomonbrys.kodein/index.html)!
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
     default. To use it, you must declare the dependency `com.github.salomonbrys.kodein:kodein-global:3.0.+`. You can
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
