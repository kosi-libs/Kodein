[com.github.salomonbrys.kodein](../index.md) / [ProviderKodein](index.md) / [overriddenProviderOrNull](.)

# overriddenProviderOrNull

`fun <T : Any> overriddenProviderOrNull(): () -> T`

Gets a provider from the overridden binding, if this binding overrides an existing binding.

### Parameters

`T` - The type of instance of this binding.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider yielded by the overridden binding, or null if this binding does not override an existing binding.

