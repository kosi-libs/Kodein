[com.github.salomonbrys.kodein.bindings](../index.md) / [ArgSetBinding](index.md) / [getInstance](.)

# getInstance

`fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, Set<T>>, arg: A): Set<T>`

Get an instance of type `T` function argument `A`.

Whether it's a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : The argument to use to get the instance.

**Return**
The instance of the requested type.

