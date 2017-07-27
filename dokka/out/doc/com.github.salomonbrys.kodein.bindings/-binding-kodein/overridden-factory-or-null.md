[com.github.salomonbrys.kodein.bindings](../index.md) / [BindingKodein](index.md) / [overriddenFactoryOrNull](.)

# overriddenFactoryOrNull

`abstract fun overriddenFactoryOrNull(): (Any?) -> Any`

Gets a factory from the overridden binding, if this binding overrides an existing binding.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
A factory yielded by the overridden binding, or null if this binding does not override an existing binding.

