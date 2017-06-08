[com.github.salomonbrys.kodein](index.md) / [ProviderOrNull](.)

# ProviderOrNull

`fun <T : Any> `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<*>.ProviderOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): () -> T`

Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

Whether this provider will re-create a new instance at each call or not depends on the binding scope.

T generics will be kept.

### Parameters

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider function, if the instance construction triggered a dependency loop.

**Return**
A provider, or null if no factory was found.

`fun <T : Any> `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<*>.ProviderOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`

Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

### Parameters

`T` - The type of object to retrieve with the provider.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T` or null if no factory was found.

`fun <T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.ProviderOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider for the given type and tag, or null if none is found.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a provider of `T`, or null if no provider was found.

`fun <T : Any> `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<*>.ProviderOrNull(type: `[`TypeToken`](-type-token/index.md)`<T>, tag: Any? = null): Lazy<() -> T>`

Gets a lazy provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`, or null if no factory is found.

