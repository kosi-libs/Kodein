[com.github.salomonbrys.kodein](../index.md) / [CurriedLazyKodeinFactory](index.md) / [provider](.)

# provider

`inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no factory was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`.

