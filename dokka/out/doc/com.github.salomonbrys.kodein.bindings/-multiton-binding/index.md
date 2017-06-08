[com.github.salomonbrys.kodein.bindings](../index.md) / [MultitonBinding](.)

# MultitonBinding

`class MultitonBinding<A, T : Any> : `[`Binding`](../-binding/index.md)`<A, T>`

Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.

### Parameters

`T` - The created type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `MultitonBinding(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T)`<br>Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>`<br>The type of the argument used for each value can there be a new instance. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of the created object, *used for debug print only*. |
| [creator](creator.md) | `val creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T`<br>The function that will be called the first time an instance is requested. Guaranteed to be called only once per argument. Should create a new instance. |

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
