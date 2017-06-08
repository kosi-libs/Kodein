[com.github.salomonbrys.kodein](index.md) / [factoryOrNull](.)

# factoryOrNull

`inline fun <reified A, reified T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.factoryOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>`

Gets a lazy factory for the given type, tag and argument type, or null if none is found

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

A &amp; T generics will be kept.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
A lazy property that yields either a factory of `T` or null if no factory was found.

`inline fun <reified A, reified T : Any> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.factoryOrNull(tag: Any? = null): Lazy<(A) -> T>`

Gets a lazy factory for the given type, tag and argument type, or null if none is found.

A &amp; T generics will be kept.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
A lazy property that yields a factory of `T`, or null if no factory was found.

`inline fun <reified A, reified T : Any> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.factoryOrNull(tag: Any? = null): (A) -> T`

Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.

Whether this factory will re-create a new instance at each call or not depends on the binding scope.

A &amp; T generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`T` - The type of object the factory returns.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory function, if the instance construction triggered a dependency loop.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

**Return**
A factory, or null if no factory was found.

