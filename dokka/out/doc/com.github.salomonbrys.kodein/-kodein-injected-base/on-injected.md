[com.github.salomonbrys.kodein](../index.md) / [KodeinInjectedBase](index.md) / [onInjected](.)

# onInjected

`open fun onInjected(cb: (`[`Kodein`](../-kodein/index.md)`) -> Unit): Unit`

Registers a callback to be called once the [injector](injector.md) gets injected with a [Kodein](../-kodein/index.md) object.

If the injector has already been injected, the callback will be called instantly.

The callback is guaranteed to be called only once.

### Parameters

`cb` - The callback to register