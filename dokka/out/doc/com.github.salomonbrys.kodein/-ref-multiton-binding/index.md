[com.github.salomonbrys.kodein](../index.md) / [RefMultitonBinding](.)

# RefMultitonBinding

`class RefMultitonBinding<A, T : Any> : `[`Binding`](../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<A, T>`

Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference.

### Parameters

`T` - The type of the instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `RefMultitonBinding(argType: `[`TypeToken`](../-type-token/index.md)`<A>, createdType: `[`TypeToken`](../-type-token/index.md)`<T>, refMaker: `[`RefMaker`](../-ref-maker/index.md)`, creator: `[`BindingKodein`](../../com.github.salomonbrys.kodein.bindings/-binding-kodein/index.md)`.(A) -> T)`<br>Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../-type-token/index.md)`<A>`<br>The type of the argument, *used for debug print only*. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../-type-token/index.md)`<T>`<br>The type of the created object, *used for debug print only*. |
| [creator](creator.md) | `val creator: `[`BindingKodein`](../../com.github.salomonbrys.kodein.bindings/-binding-kodein/index.md)`.(A) -> T`<br>A function that should always create a new object. |
| [refMaker](ref-maker.md) | `val refMaker: `[`RefMaker`](../-ref-maker/index.md)<br>Reference Maker that defines the kind of reference being used. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../../com.github.salomonbrys.kodein.bindings/-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../../com.github.salomonbrys.kodein.bindings/-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`BindingKodein`](../../com.github.salomonbrys.kodein.bindings/-binding-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`<A, T>, arg: A): T`<br>Get an instance of type `T` function argument `A`. |
