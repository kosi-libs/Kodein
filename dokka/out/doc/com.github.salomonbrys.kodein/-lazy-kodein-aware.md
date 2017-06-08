[com.github.salomonbrys.kodein](index.md) / [LazyKodeinAware](.)

# LazyKodeinAware

`interface LazyKodeinAware : `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)

Any class that extends this interface can use Kodein to "seamlessly" get lazy properties.

### Inherited Properties

| Name | Summary |
|---|---|
| [kodein](-lazy-kodein-aware-base/kodein.md) | `abstract val kodein: `[`LazyKodein`](-lazy-kodein/index.md)<br>A Lazy Kodein Aware class must be within reach of a LazyKodein object. |

### Extension Functions

| Name | Summary |
|---|---|
| [Factory](-factory.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.Factory(argType: `[`TypeToken`](-type-token/index.md)`<A>, type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [FactoryOrNull](-factory-or-null.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.FactoryOrNull(argType: `[`TypeToken`](-type-token/index.md)`<A>, type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [Instance](-instance.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.Instance(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [InstanceOrNull](-instance-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.InstanceOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [Provider](-provider.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.Provider(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [ProviderOrNull](-provider-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.ProviderOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [With](-with.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.With(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [WithF](-with-f.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [with](with.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [with](../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withClass](with-class.md) | `fun <T : LazyKodeinAware> T.withClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `Class` argument. |
| [withContext](../com.github.salomonbrys.kodein.android/with-context.md) | `fun LazyKodeinAware.withContext(ctx: Context): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(f: Fragment): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(f: Fragment): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(d: Dialog): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(v: View): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(l: Loader<*>): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(l: Loader<*>): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>Allows to get a lazy instance or provider property from a factory which takes a `Context` as argument. |
| [withKClass](with-k-class.md) | `fun <T : LazyKodeinAware> T.withKClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument. |
