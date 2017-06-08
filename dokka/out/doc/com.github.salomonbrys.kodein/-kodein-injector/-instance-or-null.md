[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [InstanceOrNull](.)

# InstanceOrNull

`fun <T : Any> InstanceOrNull(type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`

Creates a property delegate that will hold an instance, or null if none is found.

### Parameters

`T` - The type of object that will held by this property.

`type` - The type of object that will held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

**Return**
A property delegate that will lazily provide an instance of `T`, or null if no provider was found.

