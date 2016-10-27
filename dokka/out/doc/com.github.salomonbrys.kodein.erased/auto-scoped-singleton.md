[com.github.salomonbrys.kodein.erased](index.md) / [autoScopedSingleton](.)

# autoScopedSingleton

`inline fun <C, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.autoScopedSingleton(scope: `[`AutoScope`](../com.github.salomonbrys.kodein/-auto-scope/index.md)`<C>, noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(C) -> T): `[`CAutoScopedSingleton`](../com.github.salomonbrys.kodein/-c-auto-scoped-singleton/index.md)`<C, T>`

Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.

T generics will be erased!

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.