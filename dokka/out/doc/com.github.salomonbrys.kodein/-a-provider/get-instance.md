[com.github.salomonbrys.kodein](../index.md) / [AProvider](index.md) / [getInstance](.)

# getInstance

`fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`

Get an instance of type `T`.

Whether its a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : A Unit argument that is ignored (a provider does not take arguments).

**Return**
an instance of `T`.

`abstract fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`

Get an instance of type `T`.

Whether its a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

**Return**
an instance of `T`.

