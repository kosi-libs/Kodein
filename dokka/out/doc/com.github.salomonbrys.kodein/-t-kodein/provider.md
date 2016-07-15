[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [provider](.)

# provider

`@JvmOverloads fun provider(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): () -> Any`

Gets a provider for the given type and tag.

### Parameters

`type` - The type of object to retrieve with the returned provider.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - If no provider was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A provider.

`@JvmOverloads fun <T : Any> provider(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): () -> T`
`@JvmOverloads fun <T : Any> provider(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`

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

