[com.github.salomonbrys.kodein](../index.md) / [CurriedLazyKodeinFactory](index.md) / [instance](.)

# instance

`inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T>`

Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no factory was found.

`Kodein.DependencyLoopException` - When accessing the property, if the value construction triggered a dependency loop.

**Return**
A lazy instance of `T`.

