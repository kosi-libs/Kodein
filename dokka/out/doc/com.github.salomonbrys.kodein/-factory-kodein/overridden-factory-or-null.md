[com.github.salomonbrys.kodein](../index.md) / [FactoryKodein](index.md) / [overriddenFactoryOrNull](.)

# overriddenFactoryOrNull

`abstract fun <A, T : Any> overriddenFactoryOrNull(): (A) -> T`

Gets a factory from the overridden binding, if this binding overrides an existing binding.

### Parameters

`A` - The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.

`T` - The type of instance the returned factory creates or retrieves.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Return**
A factory yielded by the overridden binding, or null if this binding does not override an existing binding.

