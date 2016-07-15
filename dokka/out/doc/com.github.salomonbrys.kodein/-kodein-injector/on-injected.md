[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [onInjected](.)

# onInjected

`fun onInjected(cb: (`[`Kodein`](../-kodein/index.md)`) -> Unit): Unit`

Overrides [KodeinInjectedBase.onInjected](../-kodein-injected-base/on-injected.md)

Registers a callback to be called once the [injector](injector.md) gets injected with a [Kodein](../-kodein/index.md) object.

If the injector has already been injected, the callback will be called instantly.

The callback is guaranteed to be called only once.

### Parameters

`cb` - The callback to register