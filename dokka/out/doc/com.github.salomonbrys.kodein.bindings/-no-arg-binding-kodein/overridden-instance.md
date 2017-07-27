[com.github.salomonbrys.kodein.bindings](../index.md) / [NoArgBindingKodein](index.md) / [overriddenInstance](.)

# overriddenInstance

`abstract fun overriddenInstance(): Any`

Gets an instance from the overridden binding.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden binding.

