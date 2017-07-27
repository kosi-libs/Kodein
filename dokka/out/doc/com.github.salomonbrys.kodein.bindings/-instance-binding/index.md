[com.github.salomonbrys.kodein.bindings](../index.md) / [InstanceBinding](.)

# InstanceBinding

`class InstanceBinding<T : Any> : `[`NoArgBinding`](../-no-arg-binding/index.md)`<T>`

Concrete instance provider: will always return the given instance.

### Parameters

`T` - The type of the instance.

`createdType` - The type of the object, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `InstanceBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, instance: T)`<br>Concrete instance provider: will always return the given instance. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>`<br>The type of object that is created by this factory. |
| [description](description.md) | `val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |
| [instance](instance.md) | `val instance: T`<br>The object that will always be returned. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-no-arg-binding/arg-type.md) | `open val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<Unit>`<br>The type of the argument this factory will function for. |

### Functions

| Name | Summary |
|---|---|
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |
