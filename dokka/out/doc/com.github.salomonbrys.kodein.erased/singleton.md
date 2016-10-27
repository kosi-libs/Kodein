[com.github.salomonbrys.kodein.erased](index.md) / [singleton](.)

# singleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.singleton(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.() -> T): `[`AProvider`](../com.github.salomonbrys.kodein/-a-provider/index.md)`<T>`

Creates a singleton: will create an instance on first request and will subsequently always return the same instance.

T generics will be erased!

### Parameters

`T` - The created type.

`creator` - The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.

**Return**
A singleton ready to be bound.

