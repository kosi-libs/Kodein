[com.github.salomonbrys.kodein](../index.md) / [ProviderKodein](.)

# ProviderKodein

`class ProviderKodein : `[`Kodein`](../-kodein/index.md)

Kodein interface to be passed to provider or instance scope methods.

It is augmented to allow such methods to access a provider or instance from the binding it is overriding (if it is overriding).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ProviderKodein(_kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`)`<br>Kodein interface to be passed to provider or instance scope methods. |

### Functions

| Name | Summary |
|---|---|
| [overriddenInstance](overridden-instance.md) | `fun <T : Any> overriddenInstance(): T`<br>Gets an instance from the overridden binding. |
| [overriddenInstanceOrNull](overridden-instance-or-null.md) | `fun <T : Any> overriddenInstanceOrNull(): T?`<br>Gets an instance from the overridden binding, if this binding overrides an existing binding. |
| [overriddenProvider](overridden-provider.md) | `fun <T : Any> overriddenProvider(): () -> T`<br>Gets a provider from the overridden binding. |
| [overriddenProviderOrNull](overridden-provider-or-null.md) | `fun <T : Any> overriddenProviderOrNull(): () -> T`<br>Gets a provider from the overridden binding, if this binding overrides an existing binding. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `val `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](../-lazy-kodein/index.md)<br>Allows lazy retrieval from a [Kodein](../-kodein/index.md) or [KodeinAware](../-kodein-aware.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](../erased-factory.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedFactory(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [erasedFactoryOrNull](../erased-factory-or-null.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedFactoryOrNull(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found. |
| [erasedInstance](../erased-instance.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedInstance(tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedInstanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [erasedProvider](../erased-provider.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedProvider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.erasedProviderOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |
| [genericFactory](../generic-factory.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericFactory(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [genericFactoryOrNull](../generic-factory-or-null.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericFactoryOrNull(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found. |
| [genericInstance](../generic-instance.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericInstance(tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericInstanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [genericProvider](../generic-provider.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericProvider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.genericProviderOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> `[`Kodein`](../-kodein/index.md)`.withClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
| [withErased](../with-erased.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.withErased(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.withErased(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withGeneric](../with-generic.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.withGeneric(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.withGeneric(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> `[`Kodein`](../-kodein/index.md)`.withKClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<KClass<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
