[com.github.salomonbrys.kodein.bindings](../index.md) / [Binding](index.md) / [getInstance](.)

# getInstance

`abstract fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, T>, arg: A): T`

Get an instance of type `T` function argument `A`.

Whether it's a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : The argument to use to get the instance.

**Return**
The instance of the requested type.

