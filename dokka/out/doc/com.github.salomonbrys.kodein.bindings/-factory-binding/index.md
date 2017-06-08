[com.github.salomonbrys.kodein.bindings](../index.md) / [FactoryBinding](.)

# FactoryBinding

`class FactoryBinding<A, T : Any> : `[`Binding`](../-binding/index.md)`<A, T>`

Concrete factory: each time an instance is needed, the function [creator](creator.md) function will be called.

### Parameters

`A` - The argument type.

`T` - The created type.

`argType` - The type of the argument used by this factory.

`createdType` - The type of objects created by this factory.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `FactoryBinding(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T)`<br>Concrete factory: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.bindings.FactoryBinding$<init>(com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.FactoryBinding.A)), com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.FactoryBinding.T)), kotlin.Function2((com.github.salomonbrys.kodein.bindings.BindingKodein, com.github.salomonbrys.kodein.bindings.FactoryBinding.A, com.github.salomonbrys.kodein.bindings.FactoryBinding.T)))/creator) function will be called. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>`<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |
| [creator](creator.md) | `val creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T`<br>The function that will be called each time an instance is requested. Should create a new instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, T>, arg: A): T`<br>Get an instance of type `T` function argument `A`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [factoryFullName](../-binding/factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
