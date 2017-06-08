[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [FactoryOrNull](.)

# FactoryOrNull

`fun <A, T : Any> FactoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<out A>, type: `[`TypeToken`](../-type-token/index.md)`<out T>, tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`

Creates a property delegate that will hold a factory, or null if none is found.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`argType` - The type of argument the factory held by this property takes.

`type` - The type of object to retrieve with the factory held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A property delegate that will lazily provide a factory of `T`, or null if no factory was found.

