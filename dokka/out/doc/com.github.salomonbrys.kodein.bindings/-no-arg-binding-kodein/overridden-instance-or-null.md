[com.github.salomonbrys.kodein.bindings](../index.md) / [NoArgBindingKodein](index.md) / [overriddenInstanceOrNull](.)

# overriddenInstanceOrNull

`abstract fun overriddenInstanceOrNull(): Any?`

Gets an instance from the overridden binding, if this binding overrides an existing binding.

### Parameters

`T` - The type of instance of this binding.

### Exceptions

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden binding, or null if this binding does not override an existing binding.

