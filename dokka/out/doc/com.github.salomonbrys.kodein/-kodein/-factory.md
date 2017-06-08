[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [Factory](.)

# Factory

`open fun <A, T : Any> Factory(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`

Gets a factory of `T` for the given argument type, return type and tag.

### Parameters

`A` - The type of argument the returned factory takes.

`T` - The type of object to retrieve with the returned factory.

`argType` - The type of argument the returned factory takes.

`type` - The type of object to retrieve with the returned factory.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - If no factory was found.

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A factory of `T`.

