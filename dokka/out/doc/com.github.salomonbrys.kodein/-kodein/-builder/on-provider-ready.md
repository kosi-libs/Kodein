[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [onProviderReady](.)

# onProviderReady

`fun <T : Any> onProviderReady(key: `[`Key`](../-key/index.md)`<Unit, T>, cb: `[`NoArgBindingKodein`](../../../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.() -> Unit): Unit`

Adds a callback that will be called once the Kodein object is configured and instantiated.

The callback will be able to access the overridden provider binding.

### Parameters

`key` - The key that defines the overridden provider access.

`cb` - The callback.