[com.github.salomonbrys.kodein.bindings](../index.md) / [AutoScopedSingletonBinding](.)

# AutoScopedSingletonBinding

`class AutoScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](../-a-scoped-binding/index.md)`<Unit, C, T>, `[`NoArgBinding`](../-no-arg-binding/index.md)`<T>`

Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`createdType` - The singleton type.

`_scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AutoScopedSingletonBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, _scope: `[`AutoScope`](../-auto-scope/index.md)`<C>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.(C) -> T)`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<Unit>`<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-no-arg-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-no-arg-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getContextAndRegistry](get-context-and-registry.md) | `fun getContextAndRegistry(arg: Unit): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |
