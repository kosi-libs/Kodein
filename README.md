Kodein: Kotlin Dependency Injection
===================================

Kodein is a very simple and yet very useful IoC container. It's feature set is very small, making it very easy to use and configure.

Kodein allows you to:

- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Detect dependency loop (at runtime)
- Easily bind classes or interfaces to their instances or provider

Kodein does *not* allow you to:

- Automatically instantiate your dependencies via injected constructor and reflexivity. For that, you need Guice.
- Have dependency injection validated at compile time. For that, you need Dagger.


Example
-------

An example is always better than a thousand words:
```kotlin
public class Application : KodeinHolder {
    override val kodein by lazyKodein {
		bind<Dice>() with { RandomDice(0, 5) }
		bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
    }
}

public class Controller(private val app: Application) : KodeinHolder {
	override val kodein: Kodein get() = app.kodein

	private val user: DataSource by injectInstance()
}
```

Or you can have a look at the [Android demo project](https://github.com/SalomonBrys/Kodein/tree/master/AndroidDemo).

Install
-------

Maven:
```
<dependency>
    <groupId>com.github.salomonbrys.kodein</groupId>
    <artifactId>kodein</artifactId>
    <version>1.0</version>
</dependency>
```
Gradle:
```
compile 'com.github.salomonbrys.kodein:kodein:1.0'
```


Initializing Kodein
-------------------

You can initialize kodein in a variable:
```kotlin
val kodein = Kodein {
	/* Bindings */
}
```

Or you can lazily instanciate kodein when needed via a property delegate:
```kotlin
val kodein by lazyKodein {
	/* Bindings */
}
```


Bindings: Declaring dependencies
--------------------------------

Bindings are delared inside a Kodein initialization block. There are different ways to declare a bindings:

#### Provider binding
This binds a type to a provider function. Each time you need a person instance, the provider will be called.
For example, here is a binding that creates a new `Person` entry each time the IoC container needs a `Person` instance:
```kotlin
bind<Dice>() with { RandomDice(5) }
```

#### Singleton binding
This binds a type to an instance of this type that will lazily be created at first use. Therefore, the provided function will only be called once.
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

#### Tagged bindings
All bindings can be tagged to allow you to bind different instance of the same type:

```kotlin
bind<DataSource>() with instance(SqliteDS.open("path/to/main"))
bind<DataSource>("secondary") with instance(SqliteDS.open("path/to/secondary"))
```

#### Constant binding
It is often useful to bind "configuration" constants (Such contants are always tagged):
```kotlin
constant("maxThread") with 8
constant("serverURL") with "https://my.server.url"
```

#### Transitive dependency
With those lazily instanciated dependencies, a dependency (very) often needs another dependency. Such object should have their dependencies passed to their constructor. Thanks to Kotlin's killer type inference engine, Kodein makes retrieval of transitive dependencies really easy:  
Say you have the following class:
```kotlin
public class Dice(private val random: Random, private val max: Int) {
/*...*/
}
```
Then it is really easy to bind RandomDice with it's transitive dependencies, simply use `it.instance()` or `it.instance(name)`:
```kotlin
bind<Random>() with { SecureRandom() }
constant("max") with 5
bind<Dice>() with { Dice(it.instance(), it.instance("max")) }
```

#### Modules
Most IoC container allow you to define your bindings in seperate modules. This is very useful to have separate modules define their own binding instead of having only one central binding definition.  
Kodein does not have module per se but this behaviour can be easily reproduced with functions.
```kotlin
public fun Kodein.Builder.bindAPI() {
    bind<API>() with singleton { APIImpl() }
    /* other bindings */
}
```
Then, in your binding block, simply call `bindAPI()`.  
It is considered good practice to use such module functions.


Injection: Dependency retrieval
-------------------------------

There are two ways to retrieve a dependency:

#### Via a kodein object

You can retrieve a dependency via a Kodein instance:
```kotlin
val dice = kodein.instance<Dice>()
```

If you need multiple instance, you can get a provider. A provider is a function that will provide you with an instance each time it is called. Whether this is a new instance or the same depends on the binding scope.
```kotlin
val diceProvider = kodein.provider<Dice>()
/*...*/
val dice = diceProvider()
```

#### Via a lazy property
Lazy properties allow you to resolve the dependency upon first access. For Kodein lazy properties to work, the class must implements `KodeinHolder`.
```kotlin
public class Controller(private val app: Application) : KodeinHolder {
	override val kodein: Kodein get() = app.kodein

	private val ds: DataSource by injectInstance()
	private val diceProvider: () -> Dice by injectProvider()
	private val maxThread: Int by injectInstance("maxThread")
}
```


Android
-------

Kodein does work on Android (in fact, it was developed for an Android project).

To use Kodein on Android, simply:

- Declare the dependency bindings in the Android `Application` with `lazyKodein`:
```kotin
class MyApp : Application() : KodeinHolder {
	override val kodein by lazyKodein {
		bind<Dice>() with { RandomDice(0, 5) }
		bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
	}
}
```
- Apply `KodeinHolder` on all `Activities`, `Fragment`, `Service` or any other `Context` object that need injection. It is important to implement the kodein property using `Delegates.lazy` because `getApplication()` will not work at construction.
```kotlin
class MyActivity : Activity(), KodeinHolder {
	override val kodein by Delegates.lazy { (getApplication() as MyApp).kodein }
	private val ds: DataSource by injectInstance()
}

class MyFragment : Fragment(), KodeinHolder {
	override val kodein by Delegates.lazy { (getActivity().getApplication() as MyApp).kodein }
	private val ds: DataSource by injectInstance()
}
```

Have a look at the [Android demo project](https://github.com/SalomonBrys/Kodein/tree/master/AndroidDemo)!


Advanced use
------------

#### Transitive dependency scope violation
Kodein does not check for transitive dependency scope violation. Meaning that should a singleton depends on a non singleton dependency, that dependency will be resolved only once for the singleton. That is why, when defining the binding of a singleton, it should take Provider for dependencies that you are not 100% sure are singletons:
```kotlin
bind<Manager>() with singleton { Manager(it.provider(), it.provider()) }
```

#### Create your own scopes
Actually... There are no scopes. Just functions that act as provider proxy functions. If you are interested in creating your own scopes beside `singleton`, `threadSingleton` and `instance`, have a look at them in the [scopes.kt](https://github.com/SalomonBrys/Kodein/blob/master/src/main/kotlin/Scopes.kt) file.  
Basically, to create a scope, you need to create a function that returns a provider function: `(Kodein) -> T`.
