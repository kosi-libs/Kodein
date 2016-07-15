[com.github.salomonbrys.kodein](index.md) / [providerOrNull](.)

# providerOrNull

`inline fun <reified T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.providerOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`

Gets a lazy provider for the given type and tag, or null if none is found.

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
A lazy property that yields a provider of `T` or null if no provider was found.

`inline fun <reified T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.providerOrNull(tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider for the given type and tag, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a provider of `T`, or null if no provider was found.

`inline fun <reified T : Any> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.providerOrNull(tag: Any? = null): () -> T`

Gets a provider of `T` for the given type and tag, or null if none is found.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

### Parameters

`T` - The type of object the provider returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

**Return**
A provider, or null if no provider was found.

