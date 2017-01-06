[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](.)

# KodeinContainer

`interface KodeinContainer`

Container class where the bindings and their factories are stored.

In kodein, every binding is stored as a factory (thats why a scope is a function creating a factory).
Providers are special classes of factories that take Unit as parameter.

### Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | `class Builder`<br>This is where you configure the bindings. |

### Properties

| Name | Summary |
|---|---|
| [bindings](bindings.md) | `abstract val bindings: Map<`[`Key`](../-kodein/-key/index.md)`, `[`Factory`](../-factory/index.md)`<*, *>>`<br>An immutable view of the bindings map. *For inspection&amp;debug*. |
| [overriddenBindings](overridden-bindings.md) | `abstract val overriddenBindings: Map<`[`Key`](../-kodein/-key/index.md)`, List<`[`Factory`](../-factory/index.md)`<*, *>>>`<br>An immutable view of the bindings that were defined and later overridden. *For inspection&amp;debug*. |

### Functions

| Name | Summary |
|---|---|
| [factoryOrNull](factory-or-null.md) | `abstract fun factoryOrNull(key: `[`Key`](../-kodein/-key/index.md)`): (Any?) -> Any`<br>Retrieve a factory for the given key, or null if none is found. |
| [nonNullFactory](non-null-factory.md) | `open fun nonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`): (Any?) -> Any`<br>Retrieve a factory for the given key. |
| [nonNullProvider](non-null-provider.md) | `open fun nonNullProvider(bind: `[`Bind`](../-kodein/-bind/index.md)`): () -> Any`<br>Retrieve a provider for the given bind. |
| [overriddenFactoryOrNull](overridden-factory-or-null.md) | `abstract fun overriddenFactoryOrNull(key: `[`Key`](../-kodein/-key/index.md)`, overrideLevel: Int): (Any?) -> Any`<br>Retrieve an overridden factory for the given key at the given override level, if there is an overridden binding at that level. |
| [overriddenNonNullFactory](overridden-non-null-factory.md) | `open fun overriddenNonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`, overrideLevel: Int): (Any?) -> Any`<br>Retrieve an overridden factory for the given key at the given override level. |
| [overriddenNonNullProvider](overridden-non-null-provider.md) | `open fun overriddenNonNullProvider(bind: `[`Bind`](../-kodein/-bind/index.md)`, overrideLevel: Int): () -> Any`<br>Retrieve an overridden provider for the given key at the given override level. |
| [overriddenProviderOrNull](overridden-provider-or-null.md) | `open fun overriddenProviderOrNull(bind: `[`Bind`](../-kodein/-bind/index.md)`, overrideLevel: Int): () -> Any`<br>Retrieve an overridden provider for the given key at the given override level, if there is an overridden binding at that level. |
| [providerOrNull](provider-or-null.md) | `open fun providerOrNull(bind: `[`Bind`](../-kodein/-bind/index.md)`): () -> Any`<br>Retrieve a provider for the given bind, or null if none is found. |
