[com.github.salomonbrys.kodein.erased](index.md) / [factory](.)

# factory

`inline fun <reified A, reified T : Any> `[`LazyKodeinAwareBase`](../com.github.salomonbrys.kodein/-lazy-kodein-aware-base/index.md)`.factory(tag: Any? = null): Lazy<(A) -> T>`

Gets a lazy factory for the given type, tag and argument type.

A &amp; T generics will be erased!

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no factory was found.

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](../com.github.salomonbrys.kodein/-lazy-kodein/index.md) instance or a [LazyKodeinAware](../com.github.salomonbrys.kodein/-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a factory of `T`.

`inline fun <reified A, reified T : Any> `[`KodeinAwareBase`](../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.factory(tag: Any? = null): (A) -> T`

Gets a factory of `T` for the given argument type, return type and tag.

Whether this factory will re-create a new instance at each call or not depends on the binding scope.

A &amp; T generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no factory was found.

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](../com.github.salomonbrys.kodein/-kodein/index.md) instance or a [KodeinAware](../com.github.salomonbrys.kodein/-kodein-aware.md) class.

**Return**
A factory.

`inline fun <reified A, reified T : Any> `[`KodeinInjectedBase`](../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.factory(tag: Any? = null): `[`InjectedProperty`](../com.github.salomonbrys.kodein/-injected-property/index.md)`<(A) -> T>`

Gets a lazy factory for the given type, tag and argument type.

A &amp; T generics will be erased!

The returned property should not be accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Receiver**
Either a [KodeinInjector](../com.github.salomonbrys.kodein/-kodein-injector/index.md) instance or a [KodeinInjected](../com.github.salomonbrys.kodein/-kodein-injected.md) class.

**Return**
A lazy property that yields a factory of `T`.

`inline fun <reified A, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.factory(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(A) -> T): `[`CFactory`](../com.github.salomonbrys.kodein/-c-factory/index.md)`<A, T>`

Creates a factory: each time an instance is needed, the function [creator](factory.md#com.github.salomonbrys.kodein.erased$factory(com.github.salomonbrys.kodein.Kodein.Builder, kotlin.Function2((com.github.salomonbrys.kodein.Kodein, com.github.salomonbrys.kodein.erased.factory.A, com.github.salomonbrys.kodein.erased.factory.T)))/creator) function will be called.

A &amp; T generics will be erased!

### Parameters

`A` - The argument type.

`T` - The created type.

`creator` - The function that will be called each time an instance is requested. Should create a new instance.

**Return**
A factory ready to be bound.

