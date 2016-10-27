[com.github.salomonbrys.kodein.android](index.md) / [fragmentSingleton](.)

# fragmentSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.~~fragmentSingleton~~(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(Fragment) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<Fragment, T>`
**Deprecated:** Use scopedSingleton instead.

Creates a fragment scoped singleton factory, effectively a `factory { Fragment -> T }`.

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the fragment argument.

**Return**
The factory to bind.

