[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](index.md) / [factoryOrNull](.)

# factoryOrNull

`abstract fun <A, T : Any> factoryOrNull(key: `[`Key`](../-kodein/-key/index.md)`<A, T>): (A) -> T`

Retrieve a factory for the given key, or null if none is found.

### Parameters

`key` - The key to look for.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
The found factory, or null if no factory was found.

