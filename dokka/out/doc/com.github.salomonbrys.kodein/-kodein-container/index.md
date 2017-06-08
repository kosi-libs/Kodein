[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](.)

# KodeinContainer

`interface KodeinContainer`

Container class where the bindings and their factories are stored.

In kodein, every binding is stored as a factory (that's why a scope is a function creating a factory).
Providers are special classes of factories that take Unit as parameter.

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `class Builder`<br>This is where you configure the bindings. |

### Properties

| Name | Summary |
|---|---|
| [bindings](bindings.md) | `abstract val bindings: Map<`[`Key`](../-kodein/-key/index.md)`<*, *>, `[`Binding`](../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, *>>`<br>An immutable view of the bindings map. *For inspection&amp;debug*. |
| [overriddenBindings](overridden-bindings.md) | `abstract val overriddenBindings: Map<`[`Key`](../-kodein/-key/index.md)`<*, *>, List<`[`Binding`](../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, *>>>`<br>An immutable view of the bindings that were defined and later overridden. *For inspection&amp;debug*. |

### Functions

| Name | Summary |
|---|---|
| [factoryOrNull](factory-or-null.md) | `abstract fun <A, T : Any> factoryOrNull(key: `[`Key`](../-kodein/-key/index.md)`<A, T>): (A) -> T`<br>Retrieve a factory for the given key, or null if none is found. |
| [nonNullFactory](non-null-factory.md) | `open fun <A, T : Any> nonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`<A, T>): (A) -> T`<br>Retrieve a factory for the given key. |
| [nonNullProvider](non-null-provider.md) | `open fun <T : Any> nonNullProvider(bind: `[`Bind`](../-kodein/-bind/index.md)`<T>): () -> T`<br>Retrieve a provider for the given bind. |
| [overriddenFactoryOrNull](overridden-factory-or-null.md) | `abstract fun <A, T : Any> overriddenFactoryOrNull(key: `[`Key`](../-kodein/-key/index.md)`<A, T>, overrideLevel: Int): (A) -> T`<br>Retrieve an overridden factory for the given key at the given override level, if there is an overridden binding at that level. |
| [overriddenNonNullFactory](overridden-non-null-factory.md) | `open fun <A, T : Any> overriddenNonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`<A, T>, overrideLevel: Int): (A) -> T`<br>Retrieve an overridden factory for the given key at the given override level. |
| [overriddenNonNullProvider](overridden-non-null-provider.md) | `open fun <T : Any> overriddenNonNullProvider(bind: `[`Bind`](../-kodein/-bind/index.md)`<T>, overrideLevel: Int): () -> T`<br>Retrieve an overridden provider for the given key at the given override level. |
| [overriddenProviderOrNull](overridden-provider-or-null.md) | `open fun <T : Any> overriddenProviderOrNull(bind: `[`Bind`](../-kodein/-bind/index.md)`<T>, overrideLevel: Int): () -> T`<br>Retrieve an overridden provider for the given key at the given override level, if there is an overridden binding at that level. |
| [providerOrNull](provider-or-null.md) | `open fun <T : Any> providerOrNull(bind: `[`Bind`](../-kodein/-bind/index.md)`<T>): () -> T`<br>Retrieve a provider for the given bind, or null if none is found. |
