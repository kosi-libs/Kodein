[com.github.salomonbrys.kodein.bindings](index.md) / [InSet](.)

# InSet

`fun <T : Any> `[`TypeBinder`](../com.github.salomonbrys.kodein/-kodein/-builder/-type-binder/index.md)`<T>.InSet(setTypeToken: `[`TypeToken`](../com.github.salomonbrys.kodein/-type-token/index.md)`<Set<T>>): `[`TypeBinderInSet`](-type-binder-in-set/index.md)`<T, Set<T>>`

Allows to bind in an existing set binding (rather than directly as a new binding).

First part of the `bind<Type>().inSet() with binding` syntax.

### Parameters

`setTypeToken` - The type of the bound set.