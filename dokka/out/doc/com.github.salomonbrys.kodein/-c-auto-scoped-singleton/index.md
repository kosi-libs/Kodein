[com.github.salomonbrys.kodein](../index.md) / [CAutoScopedSingleton](.)

# CAutoScopedSingleton

`class CAutoScopedSingleton<out C, out T : Any> : `[`AScoped`](../-a-scoped/index.md)`<Unit, C, T>, `[`Provider`](../-provider/index.md)`<T>`

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
| [&lt;init&gt;](-init-.md) | `CAutoScopedSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, _scope: `[`AutoScope`](../-auto-scope/index.md)`<C>, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.(C) -> T)`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-provider/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-provider/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getContextAndRegistry](get-context-and-registry.md) | `fun getContextAndRegistry(arg: Unit): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-provider/get-instance.md) | `open fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |
