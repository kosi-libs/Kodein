[com.github.salomonbrys.kodein.bindings](../index.md) / [ArgSetBinding](.)

# ArgSetBinding

`class ArgSetBinding<A, T : Any> : `[`Binding`](../-binding/index.md)`<A, Set<T>>, `[`BaseMultiBinding`](../-base-multi-binding/index.md)`<A, T, Set<T>>`

Binding that holds multiple factory bindings (e.g. with argument) in a set.

### Parameters

`A` - The argument type of all bindings in the set.

`T` - The provided type of all bindings in the set.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ArgSetBinding(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, elementType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out Set<T>>)`<br>Binding that holds multiple factory bindings (e.g. with argument) in a set. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>`<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out Set<T>>`<br>The type of object that is created by this factory. |
| [elementType](element-type.md) | `val elementType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The provided type of all bindings in the set. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, Set<T>>, arg: A): Set<T>`<br>Get an instance of type `T` function argument `A`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [factoryFullName](../-binding/factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](../-binding/factory-name.md) | `abstract fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
