[com.github.salomonbrys.kodein](../index.md) / [CurriedLazyKodeinFactory](.)

# CurriedLazyKodeinFactory

`class CurriedLazyKodeinFactory<A>`

Allows to get a lazy provider or instance from a lazy factory with a curried argument.

### Parameters

`A` - The type of argument that the factory takes.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CurriedLazyKodeinFactory(kodein: () -> `[`Kodein`](../-kodein/index.md)`, arg: () -> A, argType: `[`TypeToken`](../-type-token/index.md)`<A>)`<br>Allows to get a lazy provider or instance from a lazy factory with a curried argument. |

### Properties

| Name | Summary |
|---|---|
| [arg](arg.md) | `val arg: () -> A`<br>The argument to provide to the factory when retrieving values. |
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../-type-token/index.md)`<A>`<br>The type of argument that the factory takes. |
| [kodein](kodein.md) | `val kodein: () -> `[`Kodein`](../-kodein/index.md)<br>The Kodein provider to use for retrieval. |

### Extension Functions

| Name | Summary |
|---|---|
| [Instance](../-instance.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.Instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument. |
| [InstanceOrNull](../-instance-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [Provider](../-provider.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.Provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument. |
| [ProviderOrNull](../-provider-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [provider](../provider.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <T : Any> CurriedLazyKodeinFactory<*>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
