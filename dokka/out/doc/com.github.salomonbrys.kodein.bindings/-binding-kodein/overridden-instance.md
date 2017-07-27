[com.github.salomonbrys.kodein.bindings](../index.md) / [BindingKodein](index.md) / [overriddenInstance](.)

# overriddenInstance

`open fun overriddenInstance(arg: Any?): Any`

Gets an instance from the overridden factory binding.

### Parameters

`arg` - The argument to provide to the factory to retrieve or create an instance.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden factory binding.

