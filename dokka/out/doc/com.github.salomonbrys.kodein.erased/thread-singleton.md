[com.github.salomonbrys.kodein.erased](index.md) / [threadSingleton](.)

# threadSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.threadSingleton(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.() -> T): `[`AProvider`](../com.github.salomonbrys.kodein/-a-provider/index.md)`<T>`

Creates a thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.

T generics will be erased!

### Parameters

`T` - The created type.

`creator` - The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.

**Return**
A thread singleton ready to be bound.

