[com.github.salomonbrys.kodein](../index.md) / [CurriedLazyKodeinFactory](index.md) / [providerOrNull](.)

# providerOrNull

`inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`, or null if no factory is found.

