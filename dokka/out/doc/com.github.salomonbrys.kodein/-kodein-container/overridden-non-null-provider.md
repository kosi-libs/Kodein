[com.github.salomonbrys.kodein](../index.md) / [KodeinContainer](index.md) / [overriddenNonNullProvider](.)

# overriddenNonNullProvider

`open fun overriddenNonNullProvider(bind: `[`Bind`](../-kodein/-bind/index.md)`, overrideLevel: Int): () -> Any`

Retrieve an overridden provider for the given key at the given override level.

### Parameters

`key` - The key to look for.

`overrideLevel` - The override level.
Override level 0 means the first overridden factory (not the "active" binding).

### Exceptions

`Kodein.NotFoundException` - If there was no binding overridden at that level.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
The overridden provider.

