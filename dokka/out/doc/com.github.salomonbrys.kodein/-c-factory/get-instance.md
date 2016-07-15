[com.github.salomonbrys.kodein](../index.md) / [CFactory](index.md) / [getInstance](.)

# getInstance

`fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`

Get an instance of type `T` function argument `A`.

Whether its a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : The argument to use to get the instance.

**Return**
The instance of the requested type.

