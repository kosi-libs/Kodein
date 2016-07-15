[com.github.salomonbrys.kodein](../index.md) / [CurriedLazyKodeinFactory](index.md) / [instanceOrNull](.)

# instanceOrNull

`inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?>`

Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When accessing the property, if the value construction triggered a dependency loop.

**Return**
A lazy instance of `T`, or null if no factory was found.

