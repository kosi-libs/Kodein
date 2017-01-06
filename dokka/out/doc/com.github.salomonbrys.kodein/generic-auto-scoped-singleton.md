[com.github.salomonbrys.kodein](index.md) / [genericAutoScopedSingleton](.)

# genericAutoScopedSingleton

`inline fun <C, reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.genericAutoScopedSingleton(scope: `[`AutoScope`](-auto-scope/index.md)`<C>, noinline creator: `[`ProviderKodein`](-provider-kodein/index.md)`.(C) -> T): `[`CAutoScopedSingleton`](-c-auto-scoped-singleton/index.md)`<C, T>`

Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.

T generics will be kept.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.