[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [instance](.)

# instance

`@JvmOverloads fun instance(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): Any`

Gets an instance for the given type and tag.

### Parameters

`type` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - If no provider was found.

`Kodein.DependencyLoopException` - If the value construction triggered a dependency loop.

**Return**
An instance.

`@JvmOverloads fun <T : Any> instance(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): T`
`@JvmOverloads fun <T : Any> instance(type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): T`

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

