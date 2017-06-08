[com.github.salomonbrys.kodein.bindings](../index.md) / [EagerSingletonBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`EagerSingletonBinding(builder: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T)`

Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

### Parameters

`T` - The created type.

`createdType` - The type of the created object.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.