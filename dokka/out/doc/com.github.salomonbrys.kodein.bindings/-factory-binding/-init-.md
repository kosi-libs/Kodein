[com.github.salomonbrys.kodein.bindings](../index.md) / [FactoryBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`FactoryBinding(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T)`

Concrete factory: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.bindings.FactoryBinding$<init>(com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.FactoryBinding.A)), com.github.salomonbrys.kodein.TypeToken((com.github.salomonbrys.kodein.bindings.FactoryBinding.T)), kotlin.Function2((com.github.salomonbrys.kodein.bindings.BindingKodein, com.github.salomonbrys.kodein.bindings.FactoryBinding.A, com.github.salomonbrys.kodein.bindings.FactoryBinding.T)))/creator) function will be called.

### Parameters

`A` - The argument type.

`T` - The created type.

`argType` - The type of the argument used by this factory.

`createdType` - The type of objects created by this factory.