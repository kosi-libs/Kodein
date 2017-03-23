[com.github.salomonbrys.kodein](../index.md) / [AScoped](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`AScoped(_creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.(C) -> T)`

A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md).

### Parameters

`A` - The type of argument that is needed to get a `C` context.

`C` - The type of context that will be used to get a [ScopeRegistry](../-scope-registry/index.md).

`T` - The singleton type.

`_creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.