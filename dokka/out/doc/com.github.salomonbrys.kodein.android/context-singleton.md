[com.github.salomonbrys.kodein.android](index.md) / [contextSingleton](.)

# contextSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.~~contextSingleton~~(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(Context) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<Context, T>`
**Deprecated:** Use scopedSingleton instead.

Creates a context scoped singleton factory, effectively a `factory { Context -> T }`.

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the context argument.

**Return**
The factory to bind.

