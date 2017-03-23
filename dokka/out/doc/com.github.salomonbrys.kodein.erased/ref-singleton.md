[com.github.salomonbrys.kodein.erased](index.md) / [refSingleton](.)

# refSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.refSingleton(refMaker: `[`RefMaker`](../com.github.salomonbrys.kodein/-ref-maker/index.md)`, noinline creator: `[`ProviderKodein`](../com.github.salomonbrys.kodein/-provider-kodein/index.md)`.() -> T): `[`Provider`](../com.github.salomonbrys.kodein/-provider/index.md)`<T>`

Creates a referenced singleton, will return always the same object as long as the reference is valid.

T generics will be erased!

### Parameters

`T` - The singleton type.

`refMaker` - The reference maker that will define the type of reference.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.