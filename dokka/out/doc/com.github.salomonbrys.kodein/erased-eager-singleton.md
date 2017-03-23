[com.github.salomonbrys.kodein](index.md) / [erasedEagerSingleton](.)

# erasedEagerSingleton

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.erasedEagerSingleton(noinline creator: `[`ProviderKodein`](-provider-kodein/index.md)`.() -> T): `[`Provider`](-provider/index.md)`<T>`

Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

T generics will be erased!

### Parameters

`T` - The created type.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.

**Return**
An eager singleton ready to be bound.

