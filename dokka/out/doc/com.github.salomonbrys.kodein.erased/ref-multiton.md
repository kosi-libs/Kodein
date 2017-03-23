[com.github.salomonbrys.kodein.erased](index.md) / [refMultiton](.)

# refMultiton

`inline fun <reified A, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.refMultiton(refMaker: `[`RefMaker`](../com.github.salomonbrys.kodein/-ref-maker/index.md)`, noinline creator: `[`FactoryKodein`](../com.github.salomonbrys.kodein/-factory-kodein/index.md)`.(A) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<A, T>`

Creates a referenced multiton, for the same argument, will return always the same object as long as the reference is valid.

A &amp; T generics will be erased!

### Parameters

`T` - The multiton type.

`refMaker` - The reference maker that will define the type of reference.

`creator` - A function that creates the multiton object. For the same argument, will be called only if the multiton does not already exist or if the reference is not valid anymore.