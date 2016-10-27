[com.github.salomonbrys.kodein](../index.md) / [LazyKodeinAwareBase](.)

# LazyKodeinAwareBase

`interface LazyKodeinAwareBase`

Base [LazyKodeinAware](../-lazy-kodein-aware.md) interface.

It is separate from [LazyKodeinAware](../-lazy-kodein-aware.md) because [LazyKodein](../-lazy-kodein/index.md) implements itself LazyKodeinAwareBase but not [LazyKodeinAware](../-lazy-kodein-aware.md).
This is because there are some extension functions to [LazyKodeinAware](../-lazy-kodein-aware.md) that would not make sense applied to the [LazyKodein](../-lazy-kodein/index.md) object.
For example, [LazyKodeinAware.withClass](../with-class.md), if applied to [LazyKodein](../-lazy-kodein/index.md), would create a very un-expected result.

### Properties

| Name | Summary |
|---|---|
| [kodein](kodein.md) | `abstract val kodein: `[`LazyKodein`](../-lazy-kodein/index.md)<br>A Lazy Kodein Aware class must be within reach of a LazyKodein object. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](../erased-factory.md) | `fun <A, T : Any> LazyKodeinAwareBase.erasedFactory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](../erased-factory-or-null.md) | `fun <A, T : Any> LazyKodeinAwareBase.erasedFactoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [erasedInstance](../erased-instance.md) | `fun <T : Any> LazyKodeinAwareBase.erasedInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.erasedInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [erasedProvider](../erased-provider.md) | `fun <T : Any> LazyKodeinAwareBase.erasedProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.erasedProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [factory](../factory.md) | `fun <A, T : Any> LazyKodeinAwareBase.factory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factory](../../com.github.salomonbrys.kodein.erased/factory.md) | `fun <A, T : Any> LazyKodeinAwareBase.factory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factoryOrNull](../factory-or-null.md) | `fun <A, T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [factoryOrNull](../../com.github.salomonbrys.kodein.erased/factory-or-null.md) | `fun <A, T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [genericFactory](../generic-factory.md) | `fun <A, T : Any> LazyKodeinAwareBase.genericFactory(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](../generic-factory-or-null.md) | `fun <A, T : Any> LazyKodeinAwareBase.genericFactoryOrNull(tag: Any? = null): Lazy<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found. |
| [genericInstance](../generic-instance.md) | `fun <T : Any> LazyKodeinAwareBase.genericInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.genericInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [genericProvider](../generic-provider.md) | `fun <T : Any> LazyKodeinAwareBase.genericProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.genericProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> LazyKodeinAwareBase.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <T : Any> LazyKodeinAwareBase.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance for the given type and tag. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance for the given type and tag, or null is none is found. |
| [provider](../provider.md) | `fun <T : Any> LazyKodeinAwareBase.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <T : Any> LazyKodeinAwareBase.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> LazyKodeinAwareBase.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> LazyKodeinAwareBase.with(arg: A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> LazyKodeinAwareBase.with(arg: () -> A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> LazyKodeinAwareBase.with(arg: A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withErased](../with-erased.md) | `fun <A> LazyKodeinAwareBase.withErased(arg: () -> A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> LazyKodeinAwareBase.withErased(arg: A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |
| [withGeneric](../with-generic.md) | `fun <A> LazyKodeinAwareBase.withGeneric(arg: () -> A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>`fun <A> LazyKodeinAwareBase.withGeneric(arg: A): `[`CurriedLazyKodeinFactory`](../-curried-lazy-kodein-factory/index.md)`<A>`<br>Allows to get a lazy provider or instance from a curried factory with an `A` argument. |

### Inheritors

| Name | Summary |
|---|---|
| [LazyKodein](../-lazy-kodein/index.md) | `class LazyKodein : Lazy<`[`Kodein`](../-kodein/index.md)`>, () -> `[`Kodein`](../-kodein/index.md)`, LazyKodeinAwareBase`<br>An object that wraps a Kodein `Lazy` object and acts both as a `Lazy` property delegate &amp; a function. |
| [LazyKodeinAware](../-lazy-kodein-aware.md) | `interface LazyKodeinAware : LazyKodeinAwareBase`<br>Any class that extends this interface can use Kodein to "seamlessly" get lazy properties. |
