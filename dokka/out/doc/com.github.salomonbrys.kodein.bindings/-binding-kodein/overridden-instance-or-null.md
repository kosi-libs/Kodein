[com.github.salomonbrys.kodein.bindings](../index.md) / [BindingKodein](index.md) / [overriddenInstanceOrNull](.)

# overriddenInstanceOrNull

`open fun overriddenInstanceOrNull(arg: Any?): Any?`

Gets an instance from the overridden factory binding, if this binding overrides an existing binding.

### Parameters

`arg` - The argument to provide to the factory to retrieve or create an instance.

### Exceptions

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden factory binding, or null if this binding does not override an existing binding.

