[com.github.salomonbrys.kodein.bindings](../index.md) / [BindingKodein](index.md) / [overriddenFactory](.)

# overriddenFactory

`abstract fun overriddenFactory(): (Any?) -> Any`

Gets a factory from the overridden binding.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
A factory yielded by the overridden binding.

