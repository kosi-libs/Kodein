[com.github.salomonbrys.kodein](../index.md) / [CurriedKodeinFactory](.)

# CurriedKodeinFactory

`class CurriedKodeinFactory<out A>`

Allows to get a provider or an instance from a factory with a curried argument.

A generics will be kept.

### Parameters

`A` - The type of argument that the factory takes.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CurriedKodeinFactory(kodein: () -> `[`Kodein`](../-kodein/index.md)`, arg: () -> A, argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>Allows to get a provider or an instance from a factory with a curried argument. |

### Properties

| Name | Summary |
|---|---|
| [arg](arg.md) | `val arg: () -> A`<br>A function that provides the argument that will be passed to the factory. |
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of argument that the factory takes. |
| [kodein](kodein.md) | `val kodein: () -> `[`Kodein`](../-kodein/index.md)<br>The Kodein instance to use for retrieval. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `val <A> CurriedKodeinFactory<A>.lazy: `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows lazy retrieval. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedInstance](../erased-instance.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.erasedInstance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.erasedInstanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [erasedProvider](../erased-provider.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.erasedProvider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.erasedProviderOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [genericInstance](../generic-instance.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.genericInstance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.genericInstanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [genericProvider](../generic-provider.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.genericProvider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.genericProviderOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instance](../instance.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument. |
| [instanceOrNull](../instance-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [provider](../provider.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument. |
| [providerOrNull](../provider-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <A, T : Any> CurriedKodeinFactory<A>.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found. |
