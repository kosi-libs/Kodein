[com.github.salomonbrys.kodein](index.md) / [KodeinAware](.)

# KodeinAware

`interface KodeinAware : `[`KodeinAwareBase`](-kodein-aware-base/index.md)

Any class that extends this interface can use Kodein "seamlessly".

### Inherited Properties

| Name | Summary |
|---|---|
| [kodein](-kodein-aware-base/kodein.md) | `abstract val kodein: `[`Kodein`](-kodein/index.md)<br>A Kodein Aware class must be within reach of a Kodein object. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](lazy.md) | `val `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](-lazy-kodein/index.md)<br>Allows lazy retrieval from a [Kodein](-kodein/index.md) or KodeinAware object. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](-with.md) | `fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.With(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [WithF](-with-f.md) | `fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [newInstance](new-instance.md) | `fun <T> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.newInstance(creator: `[`Kodein`](-kodein/index.md)`.() -> T): T`<br>Allows to create a new instance of an unbound object with the same API as when bounding one. |
| [with](with.md) | `fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [with](../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withClass](with-class.md) | `fun <T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `Class` argument. |
| [withContext](../com.github.salomonbrys.kodein.android/with-context.md) | `fun KodeinAware.withContext(ctx: Context): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(f: Fragment): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(f: Fragment): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(d: Dialog): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(v: View): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(l: Loader<*>): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>`fun KodeinAware.withContext(l: Loader<*>): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<Context>`<br>Allows to get an instance or a provider from a factory which takes a `Context` as argument. |
| [withKClass](with-k-class.md) | `fun <T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withKClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument. |

### Inheritors

| Name | Summary |
|---|---|
| [KodeinGlobalAware](../com.github.salomonbrys.kodein.conf/-kodein-global-aware/index.md) | `interface KodeinGlobalAware : KodeinAware`<br>A `KodeinAware` class that needs no implementation because the kodein used will be the [global](../com.github.salomonbrys.kodein.conf/global.md) One True Kodein. |
