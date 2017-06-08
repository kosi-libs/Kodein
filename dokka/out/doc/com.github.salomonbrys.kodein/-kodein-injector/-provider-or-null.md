[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [ProviderOrNull](.)

# ProviderOrNull

`fun <T : Any> ProviderOrNull(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`

Creates a property delegate that will hold a provider, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the provider held by this property.

`type` - The type of object to retrieve with the provider held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a provider of `T`, or null if no provider was found.

