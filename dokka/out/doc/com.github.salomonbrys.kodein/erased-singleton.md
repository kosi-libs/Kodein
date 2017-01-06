[com.github.salomonbrys.kodein](index.md) / [erasedSingleton](.)

# erasedSingleton

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.erasedSingleton(noinline creator: `[`ProviderKodein`](-provider-kodein/index.md)`.() -> T): `[`AProvider`](-a-provider/index.md)`<T>`

Creates a singleton: will create an instance on first request and will subsequently always return the same instance.

T generics will be erased!

### Parameters

`T` - The created type.

`creator` - The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.

**Return**
A singleton ready to be bound.

