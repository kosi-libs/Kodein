[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](index.md) / [nonNullFactory](.)

# nonNullFactory

`open fun nonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`): (Any?) -> Any`

Retrieve a factory for the given key.

### Parameters

`key` - The key to look for.

### Exceptions

`Kodein.NotFoundException` - If no factory was found.

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
The found factory.

