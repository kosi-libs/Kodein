[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [Instance](.)

# Instance

`open fun <T : Any> Instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T`

Gets an instance of `T` for the given type and tag.

### Parameters

`T` - The type of object to retrieve.

`type` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - If no provider was found.

`Kodein.DependencyLoopException` - If the value construction triggered a dependency loop.

**Return**
An instance of `T`.

