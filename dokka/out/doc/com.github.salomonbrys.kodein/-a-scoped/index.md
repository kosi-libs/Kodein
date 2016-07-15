[com.github.salomonbrys.kodein](../index.md) / [AScoped](.)

# AScoped

`abstract class AScoped<in A, out C, out T : Any> : `[`Factory`](../-factory/index.md)`<A, T>`

A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md).

### Parameters

`A` - The type of argument that is needed to get a `C` context.

`C` - The type of context that will be used to get a [ScopeRegistry](../-scope-registry/index.md).

`T` - The singleton type.

`_creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AScoped(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, factoryName: String, _creator: `[`Kodein`](../-kodein/index.md)`.(C) -> T)`<br>A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md). |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [factoryName](factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-factory/description.md) | `abstract val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-factory/full-description.md) | `abstract val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [_getContextAndRegistry](_get-context-and-registry.md) | `abstract fun _getContextAndRegistry(arg: A): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Get an instance of type `T` function argument `A`. |

### Inheritors

| Name | Summary |
|---|---|
| [CAutoScopedSingleton](../-c-auto-scoped-singleton/index.md) | `class CAutoScopedSingleton<out C, out T : Any> : AScoped<Unit, C, T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [CScopedSingleton](../-c-scoped-singleton/index.md) | `class CScopedSingleton<C, out T : Any> : AScoped<C, C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
