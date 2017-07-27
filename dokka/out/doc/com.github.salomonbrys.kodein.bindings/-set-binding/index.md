[com.github.salomonbrys.kodein.bindings](../index.md) / [SetBinding](.)

# SetBinding

`class SetBinding<T : Any> : `[`NoArgBinding`](../-no-arg-binding/index.md)`<Set<T>>, `[`BaseMultiBinding`](../-base-multi-binding/index.md)`<Unit, T, Set<T>>`

Binding that holds multiple factory bindings (e.g. with argument) in a set.

### Parameters

`T` - The provided type of all bindings in the set.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `SetBinding(elementType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out Set<T>>)`<br>Binding that holds multiple factory bindings (e.g. with argument) in a set. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out Set<T>>`<br>The type of object that is created by this factory. |
| [elementType](element-type.md) | `val elementType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The provided type of all bindings in the set. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-no-arg-binding/arg-type.md) | `open val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<Unit>`<br>The type of the argument this factory will function for. |
| [description](../-no-arg-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-no-arg-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, Set<T>>): Set<T>`<br>Get an instance of type `T`. |
