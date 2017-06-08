[com.github.salomonbrys.kodein.bindings](../index.md) / [ProviderBinding](.)

# ProviderBinding

`class ProviderBinding<T : Any> : `[`NoArgBinding`](../-no-arg-binding/index.md)`<T>`

Concrete provider: each time an instance is needed, the function [creator](creator.md) function will be called.

A provider is like a [FactoryBinding](../-factory-binding/index.md), but without argument.

### Parameters

`T` - The created type.

`createdType` - The type of objects created by this provider, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ProviderBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T)`<br>Concrete provider: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.bindings.ProviderBinding$<init>(com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.ProviderBinding.T)), kotlin.Function1((com.github.salomonbrys.kodein.bindings.NoArgBindingKodein, com.github.salomonbrys.kodein.bindings.ProviderBinding.T)))/creator) function will be called. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |
| [creator](creator.md) | `val creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T`<br>The function that will be called each time an instance is requested. Should create a new instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-no-arg-binding/arg-type.md) | `open val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in Unit>`<br>The type of the argument this factory will function for. |
| [description](../-no-arg-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-no-arg-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |
