[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](index.md) / [overriddenNonNullFactory](.)

# overriddenNonNullFactory

`open fun overriddenNonNullFactory(key: `[`Key`](../-kodein/-key/index.md)`, overrideLevel: Int): (Any?) -> Any`

Retrieve an overridden factory for the given key at the given override level.

### Parameters

`key` - The key to look for.

`overrideLevel` - The override level.
Override level 0 means the first overridden factory (not the "active" binding).

### Exceptions

`Kodein.NotFoundException` - If there was no binding overridden at that level.

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
The overridden factory.

