[com.github.salomonbrys.kodein](../index.md) / [CurriedKodeinFactory](index.md) / [provider](.)

# provider

`inline fun <reified T : Any> provider(tag: Any? = null): () -> T`

Gets a provider of `T` for the given tag from a curried factory with an `A` argument.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

### Parameters

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no factory was found.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider.

