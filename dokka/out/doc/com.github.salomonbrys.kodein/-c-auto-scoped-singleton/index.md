[com.github.salomonbrys.kodein](../index.md) / [CAutoScopedSingleton](.)

# CAutoScopedSingleton

`class CAutoScopedSingleton<out C, out T : Any> : `[`AScoped`](../-a-scoped/index.md)`<Unit, C, T>`

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
| [&lt;init&gt;](-init-.md) | `CAutoScopedSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, _scope: `[`AutoScope`](../-auto-scope/index.md)`<C>, creator: `[`Kodein`](../-kodein/index.md)`.(C) -> T)`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |

### Properties

| Name | Summary |
|---|---|
| [description](description.md) | `val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-a-scoped/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](../-a-scoped/created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [factoryName](../-a-scoped/factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [_getContextAndRegistry](_get-context-and-registry.md) | `fun _getContextAndRegistry(arg: Unit): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
