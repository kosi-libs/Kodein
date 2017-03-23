[com.github.salomonbrys.kodein](../index.md) / [CRefSingleton](index.md) / [getInstance](.)

# getInstance

`fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`

Overrides [Provider.getInstance](../-provider/get-instance.md)

Get an instance of type `T`.

Whether it's a new instance or not entirely depends on implementation.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

**Return**
an instance of `T`.

