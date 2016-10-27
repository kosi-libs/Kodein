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
| [erasedFactory](erased-factory.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedFactory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](erased-factory-or-null.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedFactoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [erasedInstance](erased-instance.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](erased-instance-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [erasedProvider](erased-provider.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](erased-provider-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.erasedProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [genericFactory](generic-factory.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericFactory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](generic-factory-or-null.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericFactoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [genericInstance](generic-instance.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](generic-instance-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [genericProvider](generic-provider.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](generic-provider-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.genericProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [with](with.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [with](../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withClass](with-class.md) | `fun <T : LazyKodeinAware> T.withClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `Class` argument. |
| [withContext](../com.github.salomonbrys.kodein.android/with-context.md) | `fun LazyKodeinAware.withContext(ctx: Context): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(f: Fragment): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(f: Fragment): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(d: Dialog): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(v: View): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(l: Loader<*>): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>`fun LazyKodeinAware.withContext(l: Loader<*>): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<Context>`<br>Allows to get a lazy instance or provider property from a factory which takes a `Context` as argument. |
| [withErased](with-erased.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withErased(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withErased(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withGeneric](with-generic.md) | `fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withGeneric(arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withGeneric(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withKClass](with-k-class.md) | `fun <T : LazyKodeinAware> T.withKClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument. |
