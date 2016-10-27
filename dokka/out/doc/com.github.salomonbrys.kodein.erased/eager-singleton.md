[com.github.salomonbrys.kodein.erased](index.md) / [eagerSingleton](.)

# eagerSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.eagerSingleton(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.() -> T): `[`AProvider`](../com.github.salomonbrys.kodein/-a-provider/index.md)`<T>`

Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

T generics will be erased!

### Parameters

`T` - The created type.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.

**Return**
An eager singleton ready to be bound.

