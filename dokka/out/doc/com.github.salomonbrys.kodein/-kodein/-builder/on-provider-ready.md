[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [onProviderReady](.)

# onProviderReady

`fun onProviderReady(key: `[`Key`](../-key/index.md)`, cb: `[`ProviderKodein`](../../-provider-kodein/index.md)`.() -> Unit): Unit`

Adds a callback that will be called once the Kodein object is configured and instantiated.

The callback will be able to access the overridden provider binding.

### Parameters

`key` - The key that defines the overridden provider access.

`cb` - The callback.