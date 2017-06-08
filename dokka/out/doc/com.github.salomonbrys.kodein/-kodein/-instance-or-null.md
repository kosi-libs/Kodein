[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [InstanceOrNull](.)

# InstanceOrNull

`open fun <T : Any> InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T?`

Gets an instance of `T` for the given type and tag, or null if none is found.

### Parameters

`type` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - If the value construction triggered a dependency loop.

**Return**
An instance of `T`, or null if no provider was found.

