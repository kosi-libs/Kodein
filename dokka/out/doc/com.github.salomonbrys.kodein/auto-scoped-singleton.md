[com.github.salomonbrys.kodein](index.md) / [autoScopedSingleton](.)

# autoScopedSingleton

`inline fun <C, reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.autoScopedSingleton(scope: `[`AutoScope`](../com.github.salomonbrys.kodein.bindings/-auto-scope/index.md)`<C>, noinline creator: `[`Kodein`](-kodein/index.md)`.(C) -> T): `[`AutoScopedSingletonBinding`](../com.github.salomonbrys.kodein.bindings/-auto-scoped-singleton-binding/index.md)`<C, T>`

Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.

T generics will be kept.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.