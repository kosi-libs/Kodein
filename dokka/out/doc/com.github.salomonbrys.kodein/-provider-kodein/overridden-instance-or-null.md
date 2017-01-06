[com.github.salomonbrys.kodein](../index.md) / [ProviderKodein](index.md) / [overriddenInstanceOrNull](.)

# overriddenInstanceOrNull

`fun <T : Any> overriddenInstanceOrNull(): T?`

Gets an instance from the overridden binding, if this binding overrides an existing binding.

### Parameters

`T` - The type of instance of this binding.

### Exceptions

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden binding, or null if this binding does not override an existing binding.

