[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [providerOrNull](.)

# providerOrNull

`@JvmOverloads fun providerOrNull(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): () -> Any`

Gets a provider for the given type and tag, or null if none is found.

### Parameters

`type` - The type of object to retrieve with the returned provider.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A provider, or null if no provider was found.

`@JvmOverloads fun <T : Any> providerOrNull(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): () -> T`
`@JvmOverloads fun <T : Any> providerOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the returned provider.

`type` - The type of object to retrieve with the returned provider.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A provider of `T`, or null if no provider was found.

