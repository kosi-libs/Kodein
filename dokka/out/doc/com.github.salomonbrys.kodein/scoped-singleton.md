[com.github.salomonbrys.kodein](index.md) / [scopedSingleton](.)

# scopedSingleton

`inline fun <reified C, reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.scopedSingleton(scope: `[`Scope`](-scope/index.md)`<C>, noinline creator: `[`Kodein`](-kodein/index.md)`.(C) -> T): `[`CScopedSingleton`](-c-scoped-singleton/index.md)`<C, T>`

Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which thie singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.