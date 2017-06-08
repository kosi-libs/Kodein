[com.github.salomonbrys.kodein.bindings](../index.md) / [AutoScopedSingletonBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`AutoScopedSingletonBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, _scope: `[`AutoScope`](../-auto-scope/index.md)`<C>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.(C) -> T)`

Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`createdType` - The singleton type.

`_scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.