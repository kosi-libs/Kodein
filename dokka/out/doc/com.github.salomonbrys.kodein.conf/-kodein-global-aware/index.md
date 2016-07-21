[com.github.salomonbrys.kodein.conf](../index.md) / [KodeinGlobalAware](.)

# KodeinGlobalAware

`interface KodeinGlobalAware : `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)

A `KodeinAware` class that needs no implementation because the kodein used will be the [global](../global.md) One True Kodein.

### Properties

| Name | Summary |
|---|---|
| [kodein](kodein.md) | `open val kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>The global One True Kodein. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](../../com.github.salomonbrys.kodein/lazy.md) | `val `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](../../com.github.salomonbrys.kodein/-lazy-kodein/index.md)<br>Allows lazy retrieval from a [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) or [KodeinAware](../../com.github.salomonbrys.kodein/-kodein-aware.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [factory](../../com.github.salomonbrys.kodein/factory.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.factory(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [factoryOrNull](../../com.github.salomonbrys.kodein/factory-or-null.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.factoryOrNull(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found. |
| [instance](../../com.github.salomonbrys.kodein/instance.md) | `fun <T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [instanceOrNull](../../com.github.salomonbrys.kodein/instance-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [provider](../../com.github.salomonbrys.kodein/provider.md) | `fun <T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [providerOrNull](../../com.github.salomonbrys.kodein/provider-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withContext](../../com.github.salomonbrys.kodein.android/with-context.md) | `fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(ctx: Context): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(f: Fragment): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(f: Fragment): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(d: Dialog): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(v: View): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(l: Loader<*>): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>`fun `[`KodeinAware`](../../com.github.salomonbrys.kodein/-kodein-aware.md)`.withContext(l: Loader<*>): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<Context>`<br>Allows to get an instance or a provider from a factory which takes a `Context` as argument. |
