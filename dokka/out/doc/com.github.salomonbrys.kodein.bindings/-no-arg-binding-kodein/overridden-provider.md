[com.github.salomonbrys.kodein.bindings](../index.md) / [NoArgBindingKodein](index.md) / [overriddenProvider](.)

# overriddenProvider

`abstract fun overriddenProvider(): () -> Any`

Gets a provider from the overridden binding.

### Parameters

`T` - The type of instance of this binding.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider yielded by the overridden binding.

