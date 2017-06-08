[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](.)

# KodeinInjector

`class KodeinInjector : `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)

An injector is an object which creates injected property delegates **before** having access to a Kodein instance.

For example, in Android, you can't access the Kodein instance before onCreate is called:

``` kotlin
class MyActivity : Activity() {
    val injector: KodeinInjector()
    val engine: Engine by injector.instance()
    val random: () -> Int by injector.provider("random")
    fun onCreate(savedInstanceState: Bundle) {
        injector.inject(appKodein()) // See Android's documentation for appKodein
        // Here, you can now access engine and random properties.
    }
}
```

### Exceptions

| Name | Summary |
|---|---|
| [UninjectedException](-uninjected-exception/index.md) | `class UninjectedException : RuntimeException`<br>Exception thrown when trying to access the [value](../-injected-property/value.md) of an [InjectedProperty](../-injected-property/index.md)
before the KodeinInjector that created this property is [injected](inject.md). |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinInjector()`<br>An injector is an object which creates injected property delegates **before** having access to a Kodein instance. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: KodeinInjector`<br>A Kodein Injected class must be within reach of a Kodein Injector object. |

### Functions

| Name | Summary |
|---|---|
| [Factory](-factory.md) | `fun <A, T : Any> Factory(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Creates an injected factory property delegate. |
| [FactoryOrNull](-factory-or-null.md) | `fun <A, T : Any> FactoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Creates a property delegate that will hold a factory, or null if none is found. |
| [Instance](-instance.md) | `fun <T : Any> Instance(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Creates an injected instance property delegate. |
| [InstanceOrNull](-instance-or-null.md) | `fun <T : Any> InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Creates a property delegate that will hold an instance, or null if none is found. |
| [Provider](-provider.md) | `fun <T : Any> Provider(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Creates an injected provider property delegate. |
| [ProviderOrNull](-provider-or-null.md) | `fun <T : Any> ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Creates a property delegate that will hold a provider, or null if none is found. |
| [inject](inject.md) | `fun inject(kodein: `[`Kodein`](../-kodein/index.md)`): Unit`<br>Will inject all properties that were created with the [injector](injector.md) with the values found in the provided Kodein object. |
| [kodein](kodein.md) | `fun kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Creates a property delegate that will hold a Kodein instance. |
| [onInjected](on-injected.md) | `fun onInjected(cb: (`[`Kodein`](../-kodein/index.md)`) -> Unit): Unit`<br>Registers a callback to be called once the [injector](injector.md) gets injected with a [Kodein](../-kodein/index.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../-with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.With(argType: `[`TypeToken`](../-type-token/index.md)`<A>, arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../-with-f.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.WithF(argType: `[`TypeToken`](../-type-token/index.md)`<A>, arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [kodein](../kodein.md) | `fun `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Gets a lazy [Kodein](../-kodein/index.md) object. |
| [with](../with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> KodeinInjector.withClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> KodeinInjector.withKClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<KClass<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `KClass` argument. |
