[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](.)

# ConfigurableKodein

`class ConfigurableKodein : `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)

A class that can be used to configure a kodein object and as a kodein object.

If you want it to be mutable, the [mutable](mutable.md) property needs to be set **before** any dependency retrieval.
The non-mutable configuration methods ([addImport](add-import.md), [addExtend](add-extend.md) &amp; [addConfig](add-config.md)) needs to happen **before** any dependency retrieval.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ConfigurableKodein()`<br>Default constructor.`ConfigurableKodein(mutable: Boolean)`<br>Convenient constructor to directly set the mutability. |

### Properties

| Name | Summary |
|---|---|
| [canConfigure](can-configure.md) | `val canConfigure: Boolean`<br>Whether or not this Kodein can be configured (meaning that it has not been used for retrieval yet. |
| [mutable](mutable.md) | `var mutable: Boolean?`<br>Whether this ConfigurableKodein can be mutated. |

### Functions

| Name | Summary |
|---|---|
| [addConfig](add-config.md) | `fun addConfig(config: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.() -> Unit): Unit`<br>Adds a configuration to the bindings that will be applied when the Kodein is constructed. |
| [addExtend](add-extend.md) | `fun addExtend(kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`, allowOverride: Boolean = false): Unit`<br>Adds the bindings of an existing kodein instance to the bindings that will be applied when the Kodein is constructed. |
| [addImport](add-import.md) | `fun addImport(module: `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)`, allowOverride: Boolean = false): Unit`<br>Adds a module to the bindings that will be applied when the Kodein is constructed. |
| [getOrConstruct](get-or-construct.md) | `fun getOrConstruct(): `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>Get the kodein instance if it has already been constructed, or construct it if not. |
| [mutateAddConfig](mutate-add-config.md) | `fun mutateAddConfig(config: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.() -> Unit): Unit`<br>Adds a configuration to the bindings that will extend the existing bindings. |
| [mutateAddExtend](mutate-add-extend.md) | `fun mutateAddExtend(kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`, allowOverride: Boolean = false): Unit`<br>Adds the bindings of an existing kodein instance that will extend the existing bindings. |
| [mutateAddImport](mutate-add-import.md) | `fun mutateAddImport(module: `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)`, allowOverride: Boolean = false): Unit`<br>Adds a module to the bindings that will extend the existing bindings. |
| [mutateReset](mutate-reset.md) | `fun mutateReset(keep: Boolean = false): Unit`<br>Reset the Kodein instance, allowing it to be configured again. Needs [mutable](mutable.md) to be true. |

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
| [withClassOf](../../com.github.salomonbrys.kodein/with-class-of.md) | `fun <T : Any> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.withClassOf(of: T): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
| [withKClassOf](../../com.github.salomonbrys.kodein/with-k-class-of.md) | `fun <T : Any> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`.withKClassOf(of: T): `[`CurriedKodeinFactory`](../../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<KClass<*>>`<br>Allows to get a provider or an instance from a curried factory with a `Class` argument. |
