[com.github.salomonbrys.kodein.bindings](../index.md) / [ProviderBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`ProviderBinding(createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T)`

Concrete provider: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.bindings.ProviderBinding$<init>(com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.ProviderBinding.T)), kotlin.Function1((com.github.salomonbrys.kodein.bindings.NoArgBindingKodein, com.github.salomonbrys.kodein.bindings.ProviderBinding.T)))/creator) function will be called.

A provider is like a [FactoryBinding](../-factory-binding/index.md), but without argument.

### Parameters

`T` - The created type.

`createdType` - The type of objects created by this provider, *used for debug print only*.