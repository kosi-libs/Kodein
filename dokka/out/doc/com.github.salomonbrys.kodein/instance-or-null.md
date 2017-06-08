[com.github.salomonbrys.kodein](index.md) / [instanceOrNull](.)

# instanceOrNull

`inline fun <reified T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.instanceOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T?>`

Gets a lazy instance for the given type and tag.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
A lazy property that yields a `T`.

`inline fun <reified T : Any> `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`

Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

**Return**
A lazy property that yields a `T` or null if no factory was found.

`inline fun <reified T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.instanceOrNull(tag: Any? = null): Lazy<T?>`

Gets a lazy instance for the given type and tag, or null is none is found.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When accessing the property, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields an instance of `T`, or null if no provider is found.

`inline fun <reified T : Any> `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<*>.instanceOrNull(tag: Any? = null): Lazy<T?>`

Gets a lazy instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When accessing the property, if the value construction triggered a dependency loop.

**Return**
A lazy instance of `T`, or null if no factory was found.

`inline fun <reified T : Any> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.instanceOrNull(tag: Any? = null): T?`

Gets an instance of `T` for the given type and tag, or null if none is found.

Whether the returned object is a new instance at each call or not depends on the binding scope.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

**Return**
An instance, or null if no provider was found.

`inline fun <reified T : Any> `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<*>.instanceOrNull(tag: Any? = null): T?`

Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.

Whether the returned object is a new instance at each call or not depends on the binding scope.

T generics will be kept.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Return**
An instance, or null if no factory was found.

