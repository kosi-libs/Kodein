[com.github.salomonbrys.kodein.bindings](../index.md) / [ScopedSingletonBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`ScopedSingletonBinding(contextType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<C>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, _scope: `[`Scope`](../-scope/index.md)`<C>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.(C) -> T)`

Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.