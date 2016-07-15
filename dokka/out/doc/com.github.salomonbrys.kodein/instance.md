[com.github.salomonbrys.kodein](index.md) / [instance](.)

# instance

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.instance(instance: T): `[`CInstance`](-c-instance/index.md)`<T>`

Creates an instance provider: will always return the given instance.

### Parameters

`T` - The type of the instance.

`instance` - The object that will always be returned.

**Return**
An instance provider ready to be bound.

`inline fun <reified T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.instance(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T>`

Gets a lazy instance for the given type and tag.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
A lazy property that yields a `T`.

`inline fun <reified T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.instance(tag: Any? = null): Lazy<T>`

Gets a lazy instance for the given type and tag.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - When accessing the property, if no provider was found.

`Kodein.DependencyLoopException` - When accessing the property, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields an instance of `T`.

`inline fun <reified T : Any> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.instance(tag: Any? = null): T`

Gets an instance of `T` for the given type and tag.

Whether the returned object is a new instance at each call or not depends on the binding scope.

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.NotFoundException` - if no provider was found.

`Kodein.DependencyLoopException` - If the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

**Return**
An instance.

