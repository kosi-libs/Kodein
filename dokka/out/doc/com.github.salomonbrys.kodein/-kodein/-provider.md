[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [Provider](.)

# Provider

`open fun <T : Any> Provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag.

### Parameters

`T` - The type of object to retrieve with the returned provider.

`type` - The type of object to retrieve with the returned provider.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - If no provider was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A provider of `T`.

