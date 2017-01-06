[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [onFactoryReady](.)

# onFactoryReady

`fun onFactoryReady(key: `[`Key`](../-key/index.md)`, cb: `[`FactoryKodein`](../../-factory-kodein/index.md)`.() -> Unit): Unit`

Adds a callback that will be called once the Kodein object is configured and instantiated.

The callback will be able to access the overridden factory binding.

### Parameters

`key` - The key that defines the overridden factory access.

`cb` - The callback.