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

### Types

| Name | Summary |
|---|---|
| [TInjector](-t-injector/index.md) | `inner class TInjector`<br>Typed API for injection. Can be easily used in Java. |

### Exceptions

| Name | Summary |
|---|---|
| [UninjectedException](-uninjected-exception/index.md) | `class UninjectedException : `[`RuntimeException`](http://docs.oracle.com/javase/6/docs/api/java/lang/RuntimeException.html)<br>Exception thrown when trying to access the [value](../-injected-property/value.md) of an [InjectedProperty](../-injected-property/index.md)
before the KodeinInjector that created this property is [injected](inject.md). |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinInjector()`<br>An injector is an object which creates injected property delegates **before** having access to a Kodein instance. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: KodeinInjector`<br>A Kodein Injected class must be within reach of a Kodein Injector object. |
| [typed](typed.md) | `val typed: `[`TInjector`](-t-injector/index.md)<br>Allows to access all typed API (meaning the API where you provide [Type](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html), [TypeToken](../-type-token/index.md) or `Class` objects). |

### Functions

| Name | Summary |
|---|---|
| [inject](inject.md) | `fun inject(kodein: `[`Kodein`](../-kodein/index.md)`): Unit`<br>Will inject all properties that were created with the [injector](injector.md) with the values found in the provided Kodein object. |
| [kodein](kodein.md) | `fun kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Creates a property delegate that will hold a Kodein instance. |
| [onInjected](on-injected.md) | `fun onInjected(cb: (`[`Kodein`](../-kodein/index.md)`) -> Unit): Unit`<br>Registers a callback to be called once the [injector](injector.md) gets injected with a [Kodein](../-kodein/index.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](../erased-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedFactory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](../erased-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [erasedInstance](../erased-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedInstance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [erasedProvider](../erased-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedProvider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.erasedProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [genericFactory](../generic-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericFactory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](../generic-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [genericInstance](../generic-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericInstance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [genericProvider](../generic-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericProvider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.genericProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [kodein](../kodein.md) | `fun `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Gets a lazy [Kodein](../-kodein/index.md) object. |
| [with](../with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> KodeinInjector.withClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `Class` argument. |
| [withErased](../with-erased.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.withErased(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.withErased(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withGeneric](../with-generic.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.withGeneric(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.withGeneric(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> KodeinInjector.withKClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<KClass<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `KClass` argument. |
