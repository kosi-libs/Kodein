[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](index.md) / [providerOrNull](.)

# providerOrNull

`open fun <T : Any> providerOrNull(bind: `[`Bind`](../-kodein/-bind/index.md)`<T>): () -> T`

Retrieve a provider for the given bind, or null if none is found.

### Parameters

`bind` - The bind (type and tag) to look for.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
The found provider, or null if no provider was found.

