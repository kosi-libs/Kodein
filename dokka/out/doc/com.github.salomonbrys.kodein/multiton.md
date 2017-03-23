[com.github.salomonbrys.kodein](index.md) / [multiton](.)

# multiton

`inline fun <reified A, reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.multiton(noinline creator: `[`FactoryKodein`](-factory-kodein/index.md)`.(A) -> T): `[`Factory`](-factory/index.md)`<A, T>`

Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.

A &amp; T generics will be kept.

### Parameters

`A` - The argument type.

`T` - The created type.

`creator` - The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.

**Return**
A multiton ready to be bound.

