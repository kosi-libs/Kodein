[com.github.salomonbrys.kodein](../index.md) / [KodeinInjectedBase](index.md) / [inject](.)

# inject

`open fun inject(kodein: `[`Kodein`](../-kodein/index.md)`): Unit`

Will inject all properties that were created with the [injector](injector.md) with the values found in the provided Kodein object.

Will also call all callbacks that were registered with [onInjected](on-injected.md).

### Parameters

`kodein` - The kodein object to use to inject all properties.

### Exceptions

`Kodein.NotFoundException` - if one the properties is requesting a value that is not bound.

`Kodein.DependencyLoopException` - if one the instance properties is requesting a value whose construction triggered a dependency loop.