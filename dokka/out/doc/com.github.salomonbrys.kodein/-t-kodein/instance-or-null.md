[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [instanceOrNull](.)

# instanceOrNull

`@JvmOverloads fun instanceOrNull(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): Any?`

Gets an instance for the given type and tag, or null if none is found.

### Parameters

`type` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - If the value construction triggered a dependency loop.

**Return**
An instance, or null if no provider was found.

`@JvmOverloads fun <T : Any> instanceOrNull(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): T?`
`@JvmOverloads fun <T : Any> instanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T?`

Gets an instance of `T` for the given type and tag, or null if none is found.

### Parameters

`type` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - If the value construction triggered a dependency loop.

**Return**
An instance of `T`, or null if no provider was found.

