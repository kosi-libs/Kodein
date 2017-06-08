[com.github.salomonbrys.kodein](../index.md) / [Kodein](.)

# Kodein

`interface Kodein : `[`KodeinAwareBase`](../-kodein-aware-base/index.md)

KOtlin DEpendency INjection.

To construct a Kodein instance, simply use [it's block constructor](invoke.md) and define your bindings in it :

``` kotlin
val kodein = Kodein {
    bind<Dice>() with factory { sides: Int -> RandomDice(sides) }
    bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
    bind<Random>() with provider { SecureRandom() }
    constant("answer") with "forty-two"
}
```

### Types

| Name | Summary |
|---|---|
| [Bind](-bind/index.md) | `data class Bind<out T : Any>`<br>Part of a [Key](-key/index.md) that represents the left part of a bind declaration. |
| [Builder](-builder/index.md) | `class Builder`<br>Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`. |
| [Key](-key/index.md) | `data class Key<out A, out T : Any>`<br>In Kodein, each [Binding](../../com.github.salomonbrys.kodein.bindings/-binding/index.md) is bound to a Key. A Key holds all information necessary to retrieve a factory (and therefore an instance). |
| [Module](-module/index.md) | `class Module`<br>A module is constructed the same way as in Kodein is: |

### Annotations

| Name | Summary |
|---|---|
| [KodeinDsl](-kodein-dsl/index.md) | `annotation class KodeinDsl`<br>Defines a kodein DSL function |

### Exceptions

| Name | Summary |
|---|---|
| [DependencyLoopException](-dependency-loop-exception.md) | `class DependencyLoopException : RuntimeException`<br>Exception thrown when there is a dependency loop. |
| [NotFoundException](-not-found-exception/index.md) | `class NotFoundException : RuntimeException`<br>Exception thrown when asked for a dependency that cannot be found. |
| [OverridingException](-overriding-exception/index.md) | `class OverridingException : RuntimeException`<br>Exception thrown when there is an overriding error. |

### Properties

| Name | Summary |
|---|---|
| [container](container.md) | `abstract val container: `[`KodeinContainer`](../-kodein-container/index.md)<br>Every methods eventually ends up to a call to this container. |
| [kodein](kodein.md) | `open val kodein: Kodein`<br>Defined only to conform to [KodeinAwareBase](../-kodein-aware-base/index.md). |

### Functions

| Name | Summary |
|---|---|
| [Factory](-factory.md) | `open fun <A, T : Any> Factory(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [FactoryOrNull](-factory-or-null.md) | `open fun <A, T : Any> FactoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or null if none is found. |
| [Instance](-instance.md) | `open fun <T : Any> Instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [InstanceOrNull](-instance-or-null.md) | `open fun <T : Any> InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [Provider](-provider.md) | `open fun <T : Any> Provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [ProviderOrNull](-provider-or-null.md) | `open fun <T : Any> ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |

### Companion Object Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `operator fun invoke(allowSilentOverride: Boolean = false, init: `[`Builder`](-builder/index.md)`.() -> Unit): Kodein`<br>Creates a Kodein instance. |
| [withDelayedCallbacks](with-delayed-callbacks.md) | `fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: `[`Builder`](-builder/index.md)`.() -> Unit): Pair<Kodein, () -> Unit>`<br>Creates a Kodein object but without directly calling onReady callbacks. |

### Extension Properties

| Name | Summary |
|---|---|
| [jx](../../com.github.salomonbrys.kodein.jxinject/jx.md) | `val Kodein.jx: `[`JxInjector`](../../com.github.salomonbrys.kodein.jxinject/-jx-injector/index.md)<br>Utility function that eases the retrieval of a [JxInjector](../../com.github.salomonbrys.kodein.jxinject/-jx-injector/index.md). |
| [lazy](../lazy.md) | `val `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](../-lazy-kodein/index.md)<br>Allows lazy retrieval from a Kodein or [KodeinAware](../-kodein-aware.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../-with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.With(argType: `[`TypeToken`](../-type-token/index.md)`<A>, arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../-with-f.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](../-type-token/index.md)`<A>, arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [newInstance](../new-instance.md) | `fun <T> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.newInstance(creator: Kodein.() -> T): T`<br>Allows to create a new instance of an unbound object with the same API as when bounding one. |
| [with](../with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> Kodein.withClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> Kodein.withKClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<KClass<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |

### Companion Object Extension Properties

| Name | Summary |
|---|---|
| [global](../../com.github.salomonbrys.kodein.conf/global.md) | `val Kodein.Companion.global: `[`ConfigurableKodein`](../../com.github.salomonbrys.kodein.conf/-configurable-kodein/index.md)<br>A global One True Kodein. |

### Companion Object Extension Functions

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `fun Kodein.Companion.lazy(allowSilentOverride: Boolean = false, f: `[`Builder`](-builder/index.md)`.() -> Unit): `[`LazyKodein`](../-lazy-kodein/index.md)<br>You can use the result of this function as a property delegate *or* as a function. |

### Inheritors

| Name | Summary |
|---|---|
| [BindingKodein](../../com.github.salomonbrys.kodein.bindings/-binding-kodein/index.md) | `interface BindingKodein : Kodein`<br>Kodein interface to be passed to factory scope methods. |
| [ConfigurableKodein](../../com.github.salomonbrys.kodein.conf/-configurable-kodein/index.md) | `class ConfigurableKodein : Kodein`<br>A class that can be used to configure a kodein object and as a kodein object. |
| [NoArgBindingKodein](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md) | `interface NoArgBindingKodein : Kodein`<br>Kodein interface to be passed to provider or instance scope methods. |
