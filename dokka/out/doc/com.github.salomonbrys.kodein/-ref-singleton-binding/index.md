[com.github.salomonbrys.kodein](../index.md) / [RefSingletonBinding](.)

# RefSingletonBinding

`class RefSingletonBinding<T : Any> : `[`NoArgBinding`](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding/index.md)`<T>`

Concrete referenced singleton provider: will always return the instance managed by the given reference.

### Parameters

`T` - The type of the instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RefSingletonBinding(createdType: `[`TypeToken`](../-type-token/index.md)`<T>, refMaker: `[`RefMaker`](../-ref-maker/index.md)`, creator: `[`NoArgBindingKodein`](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.() -> T)`<br>Concrete referenced singleton provider: will always return the instance managed by the given reference. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../-type-token/index.md)`<T>`<br>The type of object that is created by this factory. |
| [creator](creator.md) | `val creator: `[`NoArgBindingKodein`](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.() -> T`<br>A function that should always create a new object. |
| [refMaker](ref-maker.md) | `val refMaker: `[`RefMaker`](../-ref-maker/index.md)<br>Reference Maker that defines the kind of reference being used. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding/arg-type.md) | `open val argType: `[`TypeToken`](../-type-token/index.md)`<in Unit>`<br>The type of the argument this factory will function for. |
| [description](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`NoArgBindingKodein`](../../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |
