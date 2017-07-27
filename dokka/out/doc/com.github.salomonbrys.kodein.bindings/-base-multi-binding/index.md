[com.github.salomonbrys.kodein.bindings](../index.md) / [BaseMultiBinding](.)

# BaseMultiBinding

`abstract class BaseMultiBinding<A, T : Any, C : Any> : `[`Binding`](../-binding/index.md)`<A, C>`

Base class for binding set.

### Parameters

`A` - The argument type of all bindings in the set.

`T` - The provided type of all bindings in the set.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseMultiBinding()`<br>Base class for binding set. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-binding/arg-type.md) | `abstract val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>`<br>The type of the argument this factory will function for. |
| [createdType](../-binding/created-type.md) | `abstract val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |
| [description](../-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryName](factory-name.md) | `open fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |

### Inherited Functions

| Name | Summary |
|---|---|
| [factoryFullName](../-binding/factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |

### Inheritors

| Name | Summary |
|---|---|
| [ArgSetBinding](../-arg-set-binding/index.md) | `class ArgSetBinding<A, T : Any> : `[`Binding`](../-binding/index.md)`<A, Set<T>>, BaseMultiBinding<A, T, Set<T>>`<br>Binding that holds multiple factory bindings (e.g. with argument) in a set. |
| [SetBinding](../-set-binding/index.md) | `class SetBinding<T : Any> : `[`NoArgBinding`](../-no-arg-binding/index.md)`<Set<T>>, BaseMultiBinding<Unit, T, Set<T>>`<br>Binding that holds multiple factory bindings (e.g. with argument) in a set. |
