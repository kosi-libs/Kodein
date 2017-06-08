[com.github.salomonbrys.kodein.bindings](../index.md) / [ProviderBinding](index.md) / [getInstance](.)

# getInstance

`fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`

Get an instance of type `T`.

Whether it's a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

**Return**
an instance of `T`.

