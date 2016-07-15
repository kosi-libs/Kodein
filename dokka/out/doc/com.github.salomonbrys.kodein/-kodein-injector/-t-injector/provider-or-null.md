[com.github.salomonbrys.kodein](../../index.md) / [KodeinInjector](../index.md) / [TInjector](index.md) / [providerOrNull](.)

# providerOrNull

`@JvmOverloads fun providerOrNull(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> Any>`

Creates a property delegate that will hold a provider, or null if none is found.

### Parameters

`type` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a provider, or null if no provider was found.

`@JvmOverloads fun <T : Any> providerOrNull(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> T>`
`@JvmOverloads fun <T : Any> providerOrNull(type: `[`TypeToken`](../../-type-token/index.md)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> T>`

Creates a property delegate that will hold a provider, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`type` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a provider of `T`, or null if no provider was found.

