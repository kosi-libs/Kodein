[com.github.salomonbrys.kodein.bindings](../index.md) / [NoArgBindingKodein](index.md) / [overriddenProviderOrNull](.)

# overriddenProviderOrNull

`abstract fun overriddenProviderOrNull(): () -> Any`

Gets a provider from the overridden binding, if this binding overrides an existing binding.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider yielded by the overridden binding, or null if this binding does not override an existing binding.

