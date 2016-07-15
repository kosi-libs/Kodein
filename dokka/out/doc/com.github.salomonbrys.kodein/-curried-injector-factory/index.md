[com.github.salomonbrys.kodein](../index.md) / [CurriedInjectorFactory](.)

# CurriedInjectorFactory

`class CurriedInjectorFactory<A>`

Used to inject lazy providers or instances for factory bound types.

### Parameters

`A` - The type of argument that the factory takes.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CurriedInjectorFactory(injector: `[`KodeinInjector`](../-kodein-injector/index.md)`, arg: () -> A, argType: `[`TypeToken`](../-type-token/index.md)`<A>)`<br>Used to inject lazy providers or instances for factory bound types. |

### Properties

| Name | Summary |
|---|---|
| [arg](arg.md) | `val arg: () -> A`<br>A function that provides the argument that will be passed to the factory. |
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../-type-token/index.md)`<A>`<br>The type of argument that the factory takes. |
| [injector](injector.md) | `val injector: `[`KodeinInjector`](../-kodein-injector/index.md)<br>The injector to use for injections. |

### Functions

| Name | Summary |
|---|---|
| [instance](instance.md) | `fun <T : Any> instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [instanceOrNull](instance-or-null.md) | `fun <T : Any> instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [provider](provider.md) | `fun <T : Any> provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [providerOrNull](provider-or-null.md) | `fun <T : Any> providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
