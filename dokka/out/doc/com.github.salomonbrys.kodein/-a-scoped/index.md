[com.github.salomonbrys.kodein](../index.md) / [AScoped](.)

# AScoped

`abstract class AScoped<in A, out C, out T : Any>`

A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md).

### Parameters

`A` - The type of argument that is needed to get a `C` context.

`C` - The type of context that will be used to get a [ScopeRegistry](../-scope-registry/index.md).

`T` - The singleton type.

`_creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AScoped(_creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.(C) -> T)`<br>A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md). |

### Functions

| Name | Summary |
|---|---|
| [getContextAndRegistry](get-context-and-registry.md) | `abstract fun getContextAndRegistry(arg: A): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getScopedInstance](get-scoped-instance.md) | `fun getScopedInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Finds an instance inside a scope, or creates it if needs be. |

### Inheritors

| Name | Summary |
|---|---|
| [CAutoScopedSingleton](../-c-auto-scoped-singleton/index.md) | `class CAutoScopedSingleton<out C, out T : Any> : AScoped<Unit, C, T>, `[`Provider`](../-provider/index.md)`<T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [CScopedSingleton](../-c-scoped-singleton/index.md) | `class CScopedSingleton<C, out T : Any> : AScoped<C, C, T>, `[`Factory`](../-factory/index.md)`<C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
