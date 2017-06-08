[com.github.salomonbrys.kodein.bindings](../index.md) / [BindingKodein](.)

# BindingKodein

`interface BindingKodein : `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)

Kodein interface to be passed to factory scope methods.

It is augmented to allow such methods to access a factory or instance from the binding it is overriding (if it is overriding).

### Inherited Properties

| Name | Summary |
|---|---|
| [container](../../com.github.salomonbrys.kodein/-kodein/container.md) | `abstract val container: `[`KodeinContainer`](../../com.github.salomonbrys.kodein/-kodein-container/index.md)<br>Every methods eventually ends up to a call to this container. |
| [kodein](../../com.github.salomonbrys.kodein/-kodein/kodein.md) | `open val kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>Defined only to conform to [KodeinAwareBase](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md). |

### Functions

| Name | Summary |
|---|---|
| [overriddenFactory](overridden-factory.md) | `abstract fun overriddenFactory(): (Any?) -> Any`<br>Gets a factory from the overridden binding. |
| [overriddenFactoryOrNull](overridden-factory-or-null.md) | `abstract fun overriddenFactoryOrNull(): (Any?) -> Any`<br>Gets a factory from the overridden binding, if this binding overrides an existing binding. |
| [overriddenInstance](overridden-instance.md) | `open fun overriddenInstance(arg: Any?): Any`<br>Gets an instance from the overridden factory binding. |
| [overriddenInstanceOrNull](overridden-instance-or-null.md) | `open fun overriddenInstanceOrNull(arg: Any?): Any?`<br>Gets an instance from the overridden factory binding, if this binding overrides an existing binding. |

### Inherited Functions

| Name | Summary |
|---|---|
| [Factory](../../com.github.salomonbrys.kodein/-kodein/-factory.md) | `open fun <A, T : Any> Factory(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out A>, type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [FactoryOrNull](../../com.github.salomonbrys.kodein/-kodein/-factory-or-null.md) | `open fun <A, T : Any> FactoryOrNull(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out A>, type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or null if none is found. |
| [Instance](../../com.github.salomonbrys.kodein/-kodein/-instance.md) | `open fun <T : Any> Instance(type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [InstanceOrNull](../../com.github.salomonbrys.kodein/-kodein/-instance-or-null.md) | `open fun <T : Any> InstanceOrNull(type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [Provider](../../com.github.salomonbrys.kodein/-kodein/-provider.md) | `open fun <T : Any> Provider(type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [ProviderOrNull](../../com.github.salomonbrys.kodein/-kodein/-provider-or-null.md) | `open fun <T : Any> ProviderOrNull(type: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |

### Extension Properties

| Name | Summary |
|---|---|
| [jx](../../com.github.salomonbrys.kodein.jxinject/jx.md) | `val `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.jx: `[`JxInjector`](../../com.github.salomonbrys.kodein.jxinject/-jx-injector/index.md)<br>Utility function that eases the retrieval of a [JxInjector](../../com.github.salomonbrys.kodein.jxinject/-jx-injector/index.md). |
| [lazy](../../com.github.salomonbrys.kodein/lazy.md) | `val `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](../../com.github.salomonbrys.kodein/-lazy-kodein/index.md)<br>Allows lazy retrieval from a [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) or [KodeinAware](../../com.github.salomonbrys.kodein/-kodein-aware.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../../com.github.salomonbrys.kodein/-with.md) | `fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.With(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../../com.github.salomonbrys.kodein/-with-f.md) | `fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: () -> A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [newInstance](../../com.github.salomonbrys.kodein/new-instance.md) | `fun <T> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.newInstance(creator: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.() -> T): T`<br>Allows to create a new instance of an unbound object with the same API as when bounding one. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../../com.github.salomonbrys.kodein/with-class-of.md) | `fun <T : Any> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.withClassOf(of: T): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../../com.github.salomonbrys.kodein/with-k-class-of.md) | `fun <T : Any> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.withKClassOf(of: T): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<KClass<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
