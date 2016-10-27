[com.github.salomonbrys.kodein.android](index.md) / [serviceSingleton](.)

# serviceSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.~~serviceSingleton~~(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(Service) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<Service, T>`
**Deprecated:** Use scopedSingleton instead.

Creates a service scoped singleton factory, effectively a `factory { Service -> T }`.

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the service argument.

**Return**
The factory to bind.

