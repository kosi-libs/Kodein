[com.github.salomonbrys.kodein](../index.md) / [CAutoScopedSingleton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CAutoScopedSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, _scope: `[`AutoScope`](../-auto-scope/index.md)`<C>, creator: `[`Kodein`](../-kodein/index.md)`.(C) -> T)`

Concrete auto-scoped singleton provider, effectively a `provider { -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`createdType` - The singleton type.

`_scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.