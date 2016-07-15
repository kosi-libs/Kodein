
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
