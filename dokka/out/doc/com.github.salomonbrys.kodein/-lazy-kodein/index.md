[com.github.salomonbrys.kodein](../index.md) / [LazyKodein](.)

# LazyKodein

`class LazyKodein : Lazy<`[`Kodein`](../-kodein/index.md)`>, () -> `[`Kodein`](../-kodein/index.md)`, `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)

An object that wraps a Kodein `Lazy` object and acts both as a `Lazy` property delegate &amp; a function.

### Parameters

`k` - The lazy property delegate to wrap.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `LazyKodein(f: () -> `[`Kodein`](../-kodein/index.md)`)`<br>Constructor with a function (will `lazify` the function).`LazyKodein(k: Lazy<`[`Kodein`](../-kodein/index.md)`>)`<br>An object that wraps a Kodein `Lazy` object and acts both as a `Lazy` property delegate &amp; a function. |

### Properties

| Name | Summary |
|---|---|
| [kodein](kodein.md) | `val kodein: LazyKodein`<br>A Lazy Kodein Aware class must be within reach of a LazyKodein object. |

### Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `fun invoke(): `[`Kodein`](../-kodein/index.md) |

### Extension Functions

| Name | Summary |
|---|---|
| [factory](../factory.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.factory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factoryOrNull](../factory-or-null.md) | `fun <A, T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.factoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [provider](../provider.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> `[`LazyKodeinAwareBase`](../-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withClass](../with-class.md) | `fun <T : Any> LazyKodein.withClass(of: T): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to lazily retrive a provider or an instance from a curried factory with a `Class` argument. |
| [withKClass](../with-k-class.md) | `fun <T : Any> LazyKodein.withKClass(of: T): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<KClass<*>>`<br>Allows to lazily retrive a provider or an instance from a curried factory with a `KClass` argument. |
