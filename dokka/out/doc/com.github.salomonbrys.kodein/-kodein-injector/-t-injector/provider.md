[com.github.salomonbrys.kodein](../../index.md) / [KodeinInjector](../index.md) / [TInjector](index.md) / [provider](.)

# provider

`@JvmOverloads fun provider(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> Any>`

Creates an injected provider property delegate.

### Parameters

`type` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a provider.

`@JvmOverloads fun <T : Any> provider(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> T>`
`@JvmOverloads fun <T : Any> provider(type: `[`TypeToken`](../../-type-token/index.md)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<() -> T>`

Creates an injected provider property delegate.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`type` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a provider of `T`.

