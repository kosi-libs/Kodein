[com.github.salomonbrys.kodein.bindings](../index.md) / [ScopedSingletonBinding](.)

# ScopedSingletonBinding

`class ScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](../-a-scoped-binding/index.md)`<C, C, T>, `[`Binding`](../-binding/index.md)`<C, T>`

Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ScopedSingletonBinding(contextType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<C>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, _scope: `[`Scope`](../-scope/index.md)`<C>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.(C) -> T)`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<C>`<br>The type of the argument this factory will function for. |
| [contextType](context-type.md) | `val contextType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<C>`<br>The scope context type. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The singleton type. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getContextAndRegistry](get-context-and-registry.md) | `fun getContextAndRegistry(arg: C): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<C, T>, arg: C): T`<br>Get an instance of type `T` function argument `A`. |
