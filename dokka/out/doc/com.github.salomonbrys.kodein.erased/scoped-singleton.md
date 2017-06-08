[com.github.salomonbrys.kodein.erased](index.md) / [scopedSingleton](.)

# scopedSingleton

`inline fun <reified C, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.scopedSingleton(scope: `[`Scope`](../com.github.salomonbrys.kodein.bindings/-scope/index.md)`<C>, noinline creator: `[`NoArgBindingKodein`](../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.(C) -> T): `[`ScopedSingletonBinding`](../com.github.salomonbrys.kodein.bindings/-scoped-singleton-binding/index.md)`<C, T>`

Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.

C &amp; T generics will be erased!

### Parameters

`C` - The scope context type.

`T` - The singleton type.

`scope` - The scope object in which the singleton will be stored.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.