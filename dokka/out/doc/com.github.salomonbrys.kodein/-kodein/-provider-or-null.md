[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [ProviderOrNull](.)

# ProviderOrNull

`open fun <T : Any> ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the returned provider.

`type` - The type of object to retrieve with the returned provider.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A provider of `T`, or null if no provider was found.

