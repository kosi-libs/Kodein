[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](.)

# KodeinInjector

`class KodeinInjector : `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)

An injector is an object which creates injected property delegates **before** having access to a Kodein instance.

For example, in Android, you cant access the Kodein instance before onCreate is called:

```
class MyActivity : Activity() {
    val injector: KodeinInjector()
    val engine: Engine by injector.instance()
    val random: () -&gt; Int by injector.provider("random")
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
| [factory](../factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.factory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factoryOrNull](../factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.factoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [instance](../instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.instance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.instanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [kodein](../kodein.md) | `fun `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Gets a lazy [Kodein](../-kodein/index.md) object. |
| [provider](../provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.provider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.providerOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> KodeinInjector.withClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> KodeinInjector.withKClassOf(of: T): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<KClass<*>>`<br>Allows to inject a provider or an instance from a curried factory with a `KClass` argument. |
