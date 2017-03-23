[com.github.salomonbrys.kodein](../index.md) / [CScopedSingleton](.)

# CScopedSingleton

`class CScopedSingleton<C, out T : Any> : `[`AScoped`](../-a-scoped/index.md)`<C, C, T>, `[`Factory`](../-factory/index.md)`<C, T>`

Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CScopedSingleton(contextType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, _scope: `[`Scope`](../-scope/index.md)`<C>, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.(C) -> T)`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [contextType](context-type.md) | `val contextType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The scope context type. |
| [createdType](created-type.md) | `val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The singleton type. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-factory/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-factory/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getContextAndRegistry](get-context-and-registry.md) | `fun getContextAndRegistry(arg: C): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: C): T`<br>Get an instance of type `T` function argument `A`. |
