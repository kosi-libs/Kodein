[com.github.salomonbrys.kodein](../index.md) / [FactoryKodein](index.md) / [overriddenInstance](.)

# overriddenInstance

`open fun <A, T : Any> overriddenInstance(arg: A): T`

Gets an instance from the overridden factory binding.

### Parameters

`A` - The type of argument used to create or retrieve an instance by this factory binding and therefore the overridden one.

`T` - The type of instance of this binding.

`arg` - The argument to provide to the factory to retrieve or create an instance.

### Exceptions

`Kodein.NotFoundException` - if this binding does not override an existing binding.

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance yielded by the overridden factory binding.

