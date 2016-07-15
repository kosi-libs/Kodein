[com.github.salomonbrys.kodein](index.md) / [provider](.)

# provider

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.provider(noinline creator: `[`Kodein`](-kodein/index.md)`.() -> T): `[`CProvider`](-c-provider/index.md)`<T>`

Creates a factory: each time an instance is needed, the function [creator](provider.md#com.github.salomonbrys.kodein$provider(com.github.salomonbrys.kodein.Kodein.Builder, kotlin.Function1((com.github.salomonbrys.kodein.Kodein, com.github.salomonbrys.kodein.provider.T)))/creator) function will be called.

A provider is like a [factory](factory.md), but without argument.

### Parameters

`T` - The created type.

`creator` - The function that will be called each time an instance is requested. Should create a new instance.

**Return**
A provider ready to be bound.

`inline fun <reified T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.provider(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`

Gets a lazy provider for the given type and tag.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <reified T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider for the given type and tag.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no provider was found.

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a provider of `T`.

`inline fun <reified T : Any> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.provider(tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

### Parameters

`T` - The type of object the provider returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no provider was found.

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

**Return**
A provider.

