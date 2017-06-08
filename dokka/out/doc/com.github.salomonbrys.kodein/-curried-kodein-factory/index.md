[com.github.salomonbrys.kodein](../index.md) / [CurriedKodeinFactory](.)

# CurriedKodeinFactory

`class CurriedKodeinFactory<A>`

Allows to get a provider or an instance from a factory with a curried argument.

A generics will be kept.

### Parameters

`A` - The type of argument that the factory takes.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CurriedKodeinFactory(kodein: () -> `[`Kodein`](../-kodein/index.md)`, arg: () -> A, argType: `[`TypeToken`](../-type-token/index.md)`<A>)`<br>Allows to get a provider or an instance from a factory with a curried argument. |

### Properties

| Name | Summary |
|---|---|
| [arg](arg.md) | `val arg: () -> A`<br>A function that provides the argument that will be passed to the factory. |
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../-type-token/index.md)`<A>`<br>The type of argument that the factory takes. |
| [kodein](kodein.md) | `val kodein: () -> `[`Kodein`](../-kodein/index.md)<br>The Kodein instance to use for retrieval. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `val <A> CurriedKodeinFactory<A>.lazy: `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows lazy retrieval. |

### Extension Functions

| Name | Summary |
|---|---|
| [Instance](../-instance.md) | `fun <T : Any> CurriedKodeinFactory<*>.Instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [InstanceOrNull](../-instance-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [Provider](../-provider.md) | `fun <T : Any> CurriedKodeinFactory<*>.Provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [ProviderOrNull](../-provider-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> CurriedKodeinFactory<*>.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <T : Any> CurriedKodeinFactory<*>.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [provider](../provider.md) | `fun <T : Any> CurriedKodeinFactory<*>.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <T : Any> CurriedKodeinFactory<*>.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <T : Any> CurriedKodeinFactory<*>.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
