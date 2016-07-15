[com.github.salomonbrys.kodein](../index.md) / [Kodein](.)

# Kodein

`interface Kodein : `[`KodeinAwareBase`](../-kodein-aware-base/index.md)

KOtlin DEpendency INjection.

To construct a Kodein instance, simply use [its block constructor](invoke.md) and define your bindings in it :

```
val kodein = Kodein {
    bind&lt;Dice&gt;() with factory { sides: Int -&gt; RandomDice(sides) }
    bind&lt;DataSource&gt;() with singleton { SqliteDS.open("path/to/file") }
    bind&lt;Random&gt;() with provider { SecureRandom() }
    constant("answer") with "fourty-two"
}
```

### Types

| Name | Summary |
|---|---|
| [Bind](-bind/index.md) | `data class Bind`<br>Part of a [Key](-key/index.md) that represents the left part of a bind declaration. |
| [Builder](-builder/index.md) | `class Builder`<br>Allows for the DSL inside the block argument of the constructor of `Kodein` and `Kodein.Module`. |
| [Key](-key/index.md) | `data class Key`<br>In Kodein, each [Factory](../-factory/index.md) is bound to a Key. A Key holds all informations necessary to retrieve a factory (and therefore an instance). |
| [Module](-module/index.md) | `class Module`<br>A module is constructed the same way as in Kodein is: |

### Exceptions

| Name | Summary |
|---|---|
| [DependencyLoopException](-dependency-loop-exception.md) | `class DependencyLoopException : `[`RuntimeException`](http://docs.oracle.com/javase/6/docs/api/java/lang/RuntimeException.html)<br>Exception thrown when there is a dependency loop. |
| [NotFoundException](-not-found-exception/index.md) | `class NotFoundException : `[`RuntimeException`](http://docs.oracle.com/javase/6/docs/api/java/lang/RuntimeException.html)<br>Exception thrown when asked for a dependency that cannot be found. |
| [OverridingException](-overriding-exception/index.md) | `class OverridingException : `[`RuntimeException`](http://docs.oracle.com/javase/6/docs/api/java/lang/RuntimeException.html)<br>Exception thrown when there is an overriding error. |

### Properties

| Name | Summary |
|---|---|
| [container](container.md) | `abstract val container: `[`KodeinContainer`](../-kodein-container/index.md)<br>Every methods, either in this or in [TKodein](../-t-kodein/index.md) eventually ends up to a call to this container. |
| [kodein](kodein.md) | `open val kodein: Kodein`<br>Defined only to conform to [KodeinAwareBase](../-kodein-aware-base/index.md). |
| [typed](typed.md) | `abstract val typed: `[`TKodein`](../-t-kodein/index.md)<br>Allows to access all typed API (meaning the API where you provide `Type`, `TypeToken` or `Class` objects). |

### Companion Object Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | `operator fun invoke(allowSilentOverride: Boolean = false, init: `[`Builder`](-builder/index.md)`.() -> Unit): Kodein`<br>Creates a Kodein instance. |

### Extension Properties

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `val `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](../-lazy-kodein/index.md)<br>Allows lazy retrieval from a Kodein or [KodeinAware](../-kodein-aware.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [factory](../factory.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.factory(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag. |
| [factoryOrNull](../factory-or-null.md) | `fun <A, T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.factoryOrNull(tag: Any? = null): (A) -> T`<br>Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found. |
| [instance](../instance.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.instance(tag: Any? = null): T`<br>Gets an instance of `T` for the given type and tag. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.instanceOrNull(tag: Any? = null): T?`<br>Gets an instance of `T` for the given type and tag, or null if none is found. |
| [provider](../provider.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.provider(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.providerOrNull(tag: Any? = null): () -> T`<br>Gets a provider of `T` for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: () -> A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>`fun <A> `[`KodeinAwareBase`](../-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<A>`<br>Allows to get a provider or an instance from a curried factory with an `A` argument. |
| [withClassOf](../with-class-of.md) | `fun <T : Any> Kodein.withClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../with-k-class-of.md) | `fun <T : Any> Kodein.withKClassOf(of: T): `[`CurriedKodeinFactory`](../-curried-kodein-factory/index.md)`<KClass<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |

### Companion Object Extension Properties

| Name | Summary |
|---|---|
| [global](../../com.github.salomonbrys.kodein.global/global.md) | `val Kodein.Companion.global: `[`GlobalKodein`](../../com.github.salomonbrys.kodein.global/-global-kodein/index.md)<br>A global One True Kodein. |

### Companion Object Extension Functions

| Name | Summary |
|---|---|
| [lazy](../lazy.md) | `fun Kodein.Companion.lazy(f: `[`Builder`](-builder/index.md)`.() -> Unit): `[`LazyKodein`](../-lazy-kodein/index.md)<br>You can use the result of this function as a property delegate *or* as a function. |

### Inheritors

| Name | Summary |
|---|---|
| [GlobalKodein](../../com.github.salomonbrys.kodein.global/-global-kodein/index.md) | `class GlobalKodein : Kodein`<br>A class that can be used to configure a kodein object and as a kodein object. |
