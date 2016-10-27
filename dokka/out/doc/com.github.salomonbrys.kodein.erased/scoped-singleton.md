[com.github.salomonbrys.kodein.erased](index.md) / [scopedSingleton](.)

# scopedSingleton

`inline fun <reified C, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.scopedSingleton(scope: `[`Scope`](../com.github.salomonbrys.kodein/-scope/index.md)`<C>, noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(C) -> T): `[`CScopedSingleton`](../com.github.salomonbrys.kodein/-c-scoped-singleton/index.md)`<C, T>`

Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.

C &amp; T generics will be erased!

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.