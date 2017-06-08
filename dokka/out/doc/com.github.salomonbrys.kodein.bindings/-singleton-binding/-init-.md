[com.github.salomonbrys.kodein.bindings](../index.md) / [SingletonBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`SingletonBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<T>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T)`

Concrete singleton: will create an instance on first request and will subsequently always return the same instance.

### Parameters

`T` - The created type.

`createdType` - The type of the created object, *used for debug print only*.

`creator` - The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.