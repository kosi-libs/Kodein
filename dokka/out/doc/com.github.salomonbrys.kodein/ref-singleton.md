[com.github.salomonbrys.kodein](index.md) / [refSingleton](.)

# refSingleton

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.refSingleton(refMaker: `[`RefMaker`](-ref-maker/index.md)`, noinline creator: `[`NoArgBindingKodein`](../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.() -> T): `[`RefSingletonBinding`](-ref-singleton-binding/index.md)`<T>`

Creates a referenced singleton, will return always the same object as long as the reference is valid.

T generics will be kept.

### Parameters

`T` - The singleton type.

`refMaker` - The reference maker that will define the type of reference.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.