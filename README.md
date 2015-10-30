[![Kotlin M14](https://img.shields.io/badge/Kotlin-1.0.0--beta--1038-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.salomonbrys.kodein/kodein.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.salomonbrys.kodein%22)
[![Travis](https://img.shields.io/travis/SalomonBrys/Kodein.svg)](https://travis-ci.org/SalomonBrys/Kodein/builds)
[![MIT License](https://img.shields.io/github/license/salomonbrys/kodein.svg)](https://github.com/SalomonBrys/Kodein/blob/master/LICENSE.txt)
[![GitHub issues](https://img.shields.io/github/issues/SalomonBrys/Kodein.svg)](https://github.com/SalomonBrys/Kodein/issues)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-green.svg)](https://kotlinlang.slack.com/messages/kodein/)


Kodein: Kotlin Dependency Injection
===================================

Kodein is a very simple and yet very useful dependency retrieval container. It's feature set is very small, making it very easy to use and configure.

Kodein allows you to:

- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Easily bind classes or interfaces to their instances or provider
- Easily debug your dependency bindings and recursions

Kodein does *not* allow you to:

- Automatically instantiate your dependencies via injected constructor and reflexivity. For that, you need Guice.
- Have dependency injection validated at compile time. For that, you need Dagger.

Kodein is a good choice because:

- It is small, fast and optimized (makes extensive use of `inline`)
- It proposes a very simple and readable declarative DSL
- It is not subject to type erasure (like Java)
- It integrates nicely with Android
- It proposes a very kotlin-esque idiomatic API
- It can be used in plain Java



Example
-------

An example is always better than a thousand words:

```kotlin
val kodein = Kodein {
    bind<Dice>() with provider { RandomDice(0, 5) }
    bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
}

public class Controller(private val kodein: Kodein) {
    private val ds: DataSource by kodein.injectInstance()
}
```



Install
-------

Maven:

```
<dependency>
    <groupId>com.github.salomonbrys.kodein</groupId>
    <artifactId>kodein</artifactId>
    <version>2.2.0</version>
</dependency>
```

Gradle:

```
compile 'com.github.salomonbrys.kodein:kodein:2.2.0'
```

Android:

```
compile 'com.github.salomonbrys.kodein:kodein-android:2.2.0'
```

 - Version 1.1 is compatible with Kotlin M11
 - Version 1.3.0 is compatible with Kotlin M12
 - Version 1.4.0 is compatible with Kotlin M13
 - Version 2.1.1 is compatible with Kotlin M14
 - Version 2.2.0 is compatible with Kotlin 1.0.0-Beta-1038



Bindings: Declaring dependencies
--------------------------------

You can initialize kodein in a variable:

```kotlin
val kodein = Kodein {
	/* Bindings */
}
```

Bindings are delared inside a Kodein initialization block, they are not subject to type erasure (e.g. You can bind both a `List<Int>` and a `List<String>` to different list instances, provider or factory).

There are different ways to declare a bindings:


#### Factory binding

This binds a type to a factory function.  
A factory function is a function that takes an argument of a defined type and that returns an object of the binded type.  
Each time you need an instance of the binded type, the function will be called.

For example, here is a binding that creates a new `Dice` entry each time the you need a `Dice` instance, according to a given `Int` representing the number of sides:

```kotlin
bind<Dice>() with factory { sides: Int -> RandomDice(sides) }
```


#### Provider binding

This binds a type to a provider function.  
A provider function is a function that takes an argument of a defined type and that returns an object of the binded type. It's like a factory, but without arguments.  
Each time you need an instance of the binded type, the function will be called.  
For example, here is a binding that creates a new 6 sided `Dice` entry each time you need a `Dice` instance:

```kotlin
bind<Dice>() with provider { RandomDice(6) }
```


#### Singleton binding

This binds a type to an instance of this type that will lazily be created at first use. Therefore, the provided function will only be called once, the first time an instance is needed.

```kotlin
bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
```


#### Thread singleton binding

This is the same as the singleton binding, except that each thread gets a different instance. Therefore, the provided function is called once per thread needing the instance.

```kotlin
bind<Cache>() with threadSingleton { LRUCache(16 * 1024) }
```


#### Instance binding

This binds a type to an instance *already created*.

```kotlin
bind<DataSource>() with instance(SqliteDataSource.open("path/to/file"))
```

Note that instance is used with parenthesis. It is not given a function but an instance.


#### Tagged bindings

All bindings can be tagged to allow you to bind different instances of the same type:

```kotlin
bind<Dice>() with provider { RandomDice(6) }
bind<Dice>("DnD10") with provider { RandomDice(10) }
bind<Dice>("DnD20") with provider { RandomDice(20) }
```

Note that you can have multiple bindings of the same type, as long as they are binded with different tags. You can have only one binding of a type with no tag.


#### Constant binding

It is often useful to bind "configuration" constants (Such contants are always tagged):

```kotlin
constant("maxThread") with 8
constant("serverURL") with "https://my.server.url"
```

Note The absence of curly braces. It is not given a function but an instance.

You should only use constant bindings for very simple types without inheritance or interface (e.g. primitive types and data classes).


#### Transitive dependency

With those lazily instanciated dependencies, a dependency (very) often needs another dependency. Such object should have their dependencies passed to their constructor. Thanks to Kotlin's killer type inference engine, Kodein makes retrieval of transitive dependencies really easy:  
Say you have the following class:

```kotlin
/* The class Dice needs:
     - A Random implementation.
     - The number of side of the dice.
*/
public class Dice(private val random: Random, private val sides: Int) {
/*...*/
}
```

Then it is really easy to bind RandomDice with it's transitive dependencies, simply use `instance()` or `instance(tag)`:

```kotlin
bind<Random>() with provider { SecureRandom() }
constant("max") with 5
bind<Dice>() with { Dice(instance(), instance("max")) }
```

You can, of course, also use the functions `provider()`, `provider(tag)`, `factory()` and `factory(tag)`, 


#### Scoped transitive dependency

Sometimes, you may arrive to a situation where a singleton binded type depends on a provider binded type.  
Something like this :

```kotlin
class GameEngine(private val rnd: Random) { /*...*/ }
val kodein = Kodein {
    bind<Random>() with provider { MySuperSecureRandom() } // A MySuperSecureRandom instance can only give one random result!
    bind<GameEngine>() with singleton { GreatGameEngine(instance()) }
}
```

Do you see the problem? In this case, `GreatGameEngine` will receive a `MySuperSecureRandom` instance, always re-using the same instance, making `MySuperSecureRandom` essentially a singleton inside `GreatGameEngine`.

The correction is very easy:

```kotlin
class GameEngine(private val rnd: () -> Random) { /*...*/ }
```

I encourage you to follow this rule: *In a singleton, if you're not 100% sure that the transitive dependencies are themselves singletons, then use providers*.


#### Modules

Kodein allows you to export your bindings in modules. It is very useful to have separate modules define their own bindings instead of having only one central binding definition.  
A module is an object that you can construct the exact same way you construct a Kodein instance:

```kotlin
val apiModule = Kodein.Module {
    bind<API>() with singleton { APIImpl() }
    /* other bindings */
}
```

Then, in your Kodein binding block:

```kotlin
val kodein = Kodein {
    import(apiModule)
    /* other bindings */
}
```



Injection: Dependency retrieval
-------------------------------

In this chapter, these example bindings are used:

```kotlin
bind<Dice>() with factory { sides: Int -> RandomDice(sides) }
bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
bind<Random>() with provider { SecureRandom() }
constant("answer") with "fourty-two"
```


#### Retrieval rules

When retrieving a dependency, the following rule applies:

- A dependency binded with a `factory` can only be retrived as a factory method: `(A) -> T`.
- A dependency binded with a `provider`, an `instance`, a `singleton` or a `constant` can be retrived:
    * as a provider method: `() -> T`
    * as an instance: `T`


#### Via kodein methods

You can retrieve a dependency via a Kodein instance:

```kotlin
val diceFactory: (Int) -> Dice = kodein.factory()
val dataSource: DataSource = kodein.instance()
val randomProvider: () -> Random = kodein.provider()
val answerConstant: String = kodein.instance("answer")
```

When using a provider, whether the provider will give each time a new instance or the same depends on the binding scope.

When asking for a type that was not binded, `Kodein.NotFoundException` will be thrown.

If you're not sure (or simply don't know) if the type has been binded, you can use:

```kotlin
val diceFactory: ((Int) -> Dice)? = kodein.factoryOrNull()
val dataSource: DataSource? = kodein.instanceOrNull()
val randomProvider: (() -> Random)? = kodein.providerOrNull()
val answerConstant: String? = kodein.instanceOrNull("answer")
```

You can retrive a provider from a factory binded type by using `toProvider`:

```kotlin
private val sixSideDiceProvider: () -> Dice = kodein.factory().toProvider(6)
```


#### Via a lazy property

Lazy properties allow you to resolve the dependency upon first access.

```kotlin
public class Controller(private val kodein: Kodein) {
    private val diceFactory: (Int) -> Dice by kodein.lazyFactory()
    private val dataSource: DataSource by kodein.lazyInstance()
    private val randomProvider: () -> Random by kodein.lazyProvider()
    private val answerConstant: String by kodein.lazyInstance("answer")
}
```

You can retrive a provider or an instance from a factory binded type by using `toLazyProvider` and `toLazyInstance`:

```kotlin
private val sixSideDiceProvider: () -> Dice by kodein.lazyFactory().toLazyProvider(6)
private val tenSideDiceProvider: Dice by kodein.lazyFactory().toLazyInstance(10)
```


#### Via an injector

An injector is an object that you can use to inject all injected values in an object.

This allows your object to:

 - retrieve all it's injected dependencies at once.
 - declare its dependencies without a kodein instance.

```kotlin
public class Controller() {
    private val injector = KodeinInjector()

    private val diceFactory: (Int) -> Dice by injector.factory()
    private val dataSource: DataSource by injector.instance()
    private val randomProvider: () -> Random by injector.provider()
    private val answerConstant: String by injector.instance("answer")
    
    public fun whenReady(kodein: Kodein) {
        injector.inject(kodein)
    }
}
```

When accessing a property injected by an injector *before* calling `injector.inject(kodein)`, `KodeinInjector.UninjectedException` will be thrown.

You can inject a provider or an instance from a factory binded type by using `toProvider` and `toInstance`:

```kotlin
private val sixSideDiceProvider: () -> Dice by injector.factory().toProvider(6)
private val tenSideDiceProvider: Dice by injector.factory().toInstance(10)
```


#### In Java

While Kodein does not allow you to declare modules or dependencies in Java, it does allow you to retrieve dependencies using a Java API.  
Simply give `kodein.java` to your Java classes, and you can use Kodein in Java:

```java
public class JavaClass {
    private Function1<Integer, Dice> diceFactory;
    private Datasource dataSource;
    private Function0<Random> randomProvider;
    private String answerConstant;
    
    public JavaClass(JKodein kodein) {
        diceFactory = kodein.factory(Integer.class, Dice.class);
        dataSource = kodein.instance(Datasource.class);
        randomProvider = kodein.provider(Random.class);
        answerConstant = kodein.instance(String.class, "answer");
    }
}
```

Java is subject to type erasure, therefore, if you registered a generic class binding such as `bind<List<String>>()`, to retrieve it in Java, you need to use `TypeToken` to circumvent java's type erasure:

```java
List<String> list = kodein.instance(new TypeToken<List<String>>(){});
```



Android
-------

Kodein does work on Android (in fact, it was developed for an Android project). You can use Kodein as-is in your Android project or use the very small util library `kodein-android`.  

Here's how to use `kodein-android`: declare the dependency bindings in the Android `Application`, having it implements `KodeinApplication`:

```kotin
class MyApp : Application(), KodeinApplication {
	override val kodein = Kodein {
	/* bindings... */
	}
}
```

Don't forget to declare the application in the `AndroidManifest.xml` file.

Then, in your Activities, Fragments, and other context aware android classes, you can retrieve dependencies.

There are different ways to access a kodein instance and your dependencies:


#### Using appKodein

`appKodein` is a function that will work in your context aware android classes provided that your application implements `KodeinApplication`:

```kotlin
class MyActivity : Activity() {
    public val diceProvider: () -> Dice by appKodein.lazyProvider()  // appKodein without parenthesis
    
    override onCreate(savedInstanceState: Bundle?) {
        val random: Random = appKodein().instance()   // appKodein with parenthesis
    }
}
```

Note that `appKodein` is used without parenthesis to delegate lazy properties but with parenthesis to access the kodein instance.  
That's because you cannot access the Application object and therefore the kodein instance while the activity is not initialized by Android.

This method is really easy but it is not really optimized because the kodein instance will be re-fetched every time you use `appKodein` (each time inducing a cast from `Application` to `KodeinApplication`).  
However, this method is very easy and readable, I recommend you use it if you have only a few dependencies to inject.


#### Using lazyKodeinFromApp

This is a more optimized way of injecting dependencies. It works the exact same way as the previous method, except that the kodein instance will be fetched only once.

```kotlin
class MyActivity : Activity() {
    private val kodein = lazyKodeinFromApp() // Note the use of = and not by
    public val diceProvider: () -> Dice = kodein.lazyProvider()  // kodein without parenthesis
    
    override onCreate(savedInstanceState: Bundle?) {
        val random: Random = kodein().instance()   // kodein with parenthesis
    }
}
```


#### Using an injector

Using an injector allows you to resolve all dependencies in `onCreate`, reducing the cost of dependency first-access (but making more work happening in `onCreate`). As with the previous method, the Kodein instance will only be fetched once.

```kotlin
class MyActivity : Activity() {
    private val injector = KodeinInjector()

    public val diceProvider: () -> Dice by injector.provider()
    public val random: Random by injector.instance()
    
    override onCreate(savedInstanceState: Bundle?) {
        injector.inject(appKodein())
    }
}
```

Using this approach has an important advantage: as all dependencies are retrieved in `onCreate`, you can be sure that all your dependencies have correctly been retrieved, meaning that there were no dependency loop or non-declared dependency.  
You can have this certitude with the two previous methods only once you have accessed all dependencies at least once.


#### Android example project

Have a look at the [Android demo project](https://github.com/SalomonBrys/Kodein/tree/master/AndroidDemo)!



Debuging
--------


#### Print bindings

You can easily print bindings with `println(kodein.bindingsDescription)`.

Here's an example of what this prints:

```
        bind<com.test.Dice>() with factory<Kotlin.Int>
        bind<com.test.DataSource>() with singleton
        bind<java.util.Random>() with provider
        bind<java.lang.String>("answer") with instance
```

As you can see, it's really easy to understand which type with which tag is binded to which scope.


#### Recursive dependency loop

When it detects a recursive dependency, Kodein will throw a `Kodein.DependencyLoopException`. The message of the exception explains how the loop happened.

Here's an example:

```
com.github.salomonbrys.kodein.Kodein$DependencyLoopException: Dependency recursion:
       ╔═> bind<com.test.A>()
       ╠─> bind<com.test.B>()
       ╠─> bind<com.test.C>("yay")
       ╚═> bind<com.test.A>()
```

From this, we can see that

 - `com.test.A` depends on `com.test.B`
 - `com.test.B` depends on `com.test.C` with the tag "Yay"
 - `com.test.C` with the tag "Yay" depends on `com.test.A`

And we have found the dependency loop.



Advanced use
------------


#### Create your own scopes

A scope is an extension function to `Kodein.Builder` that returns a `Factory<A, T>`.  
You can use the `CFactory<A, T>` class for ease of use.  
If your scope is provider scope (such as singleton), you can use the `CProvider<T>` class for ease of use.  
Have a look at existing scopes in the [scopes.kt](https://github.com/SalomonBrys/Kodein/blob/master/kodein/src/main/kotlin/com/github/salomonbrys/kodein/scopes.kt) file. The `singleton` scope is very easy to understand and is a good starting point.


#### Bind the same type to different factories

Yeah, when I said earlier that "you can have multiple bindings of the same type, as long as they are binded with different tags", I lied. Beacause each binding is actually a factory, the bindings are not `([BindType], [Tag])` but actually `([BindType], [ArgType], [Tag])` (note that providers and singletons are binded as `([BindType], Unit, [Tag])`). This means that any combination of these three information can be binded to it's own factory, which in turns means that you can bind the same type without tag to different factories. Please be cautious when using this knowledge, other less thorough readers may get confused with it.


Let's talk!
-----------

You've read so far ?! You're awsome!
Why don't you drop by the [Kodein Slack channel](https://kotlinlang.slack.com/messages/kodein/) on kotlin's slack?
