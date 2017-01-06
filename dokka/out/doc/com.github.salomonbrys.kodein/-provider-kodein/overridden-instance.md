[com.github.salomonbrys.kodein](../index.md) / [ProviderKodein](index.md) / [overriddenInstance](.)

# overriddenInstance

`fun <T : Any> overriddenInstance(): T`

Gets an instance from the overridden binding.

### Parameters

`T` - The type of instance of this binding.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden binding.

