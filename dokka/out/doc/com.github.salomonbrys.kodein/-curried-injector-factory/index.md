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

### Extension Functions

| Name | Summary |
|---|---|
| [Instance](../-instance.md) | `fun <T : Any> CurriedInjectorFactory<*>.Instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [InstanceOrNull](../-instance-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [Provider](../-provider.md) | `fun <T : Any> CurriedInjectorFactory<*>.Provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [ProviderOrNull](../-provider-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> CurriedInjectorFactory<*>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <T : Any> CurriedInjectorFactory<*>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [provider](../provider.md) | `fun <T : Any> CurriedInjectorFactory<*>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <T : Any> CurriedInjectorFactory<*>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <T : Any> CurriedInjectorFactory<*>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
