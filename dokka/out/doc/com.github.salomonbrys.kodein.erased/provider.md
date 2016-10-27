[com.github.salomonbrys.kodein.erased](index.md) / [provider](.)

# provider

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.provider(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.() -> T): `[`CProvider`](../com.github.salomonbrys.kodein/-c-provider/index.md)`<T>`

Creates a factory: each time an instance is needed, the function [creator](provider.md#com.github.salomonbrys.kodein.erased$provider(com.github.salomonbrys.kodein.Kodein.Builder, kotlin.Function1((com.github.salomonbrys.kodein.Kodein, com.github.salomonbrys.kodein.erased.provider.T)))/creator) function will be called.

T generics will be erased!

A provider is like a [factory](factory.md), but without argument.

### Parameters

`T` - The created type.

`creator` - The function that will be called each time an instance is requested. Should create a new instance.

**Return**
A provider ready to be bound.

`inline fun <reified T : Any> `[`KodeinInjectedBase`](../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.provider(tag: Any? = null): `[`InjectedProperty`](../com.github.salomonbrys.kodein/-injected-property/index.md)`<() -> T>`

Gets a lazy provider for the given type and tag.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

T generics will be erased!

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [KodeinInjector](../com.github.salomonbrys.kodein/-kodein-injector/index.md) instance or a [KodeinInjected](../com.github.salomonbrys.kodein/-kodein-injected.md) class.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <A, reified T : Any> `[`CurriedInjectorFactory`](../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>.provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

T generics will be erased!

### Parameters

`T` - The type of object to retrieve with the provider.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../com.github.salomonbrys.kodein/-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <reified T : Any> `[`LazyKodeinAwareBase`](../com.github.salomonbrys.kodein/-lazy-kodein-aware-base/index.md)`.provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider for the given type and tag.

T generics will be erased!

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no provider was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](../com.github.salomonbrys.kodein/-lazy-kodein/index.md) instance or a [LazyKodeinAware](../com.github.salomonbrys.kodein/-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <A, reified T : Any> `[`CurriedLazyKodeinFactory`](../com.github.salomonbrys.kodein/-curried-lazy-kodein-factory/index.md)`<A>.provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument.

T generics will be erased!

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no factory was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <reified T : Any> `[`KodeinAwareBase`](../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.provider(tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

T generics will be erased!

### Parameters

`T` - The type of object the provider returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no provider was found.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](../com.github.salomonbrys.kodein/-kodein/index.md) instance or a [KodeinAware](../com.github.salomonbrys.kodein/-kodein-aware.md) class.

**Return**
A provider.

`inline fun <A, reified T : Any> `[`CurriedKodeinFactory`](../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>.provider(tag: Any? = null): () -> T`

Gets a provider of `T` for the given tag from a curried factory with an `A` argument.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

T generics will be erased!

### Parameters

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no factory was found.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider.

