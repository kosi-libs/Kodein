[com.github.salomonbrys.kodein](../index.md) / [CurriedKodeinFactory](index.md) / [providerOrNull](.)

# providerOrNull

`inline fun <reified T : Any> providerOrNull(tag: Any? = null): () -> T`

Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

### Parameters

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider, or null if no factory was found.

