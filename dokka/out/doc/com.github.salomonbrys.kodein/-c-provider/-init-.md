[com.github.salomonbrys.kodein](../index.md) / [CProvider](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CProvider(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`

Concrete provider: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.CProvider$<init>(java.lang.reflect.Type, kotlin.Function1((com.github.salomonbrys.kodein.ProviderKodein, com.github.salomonbrys.kodein.CProvider.T)))/creator) function will be called.

A provider is like a [CFactory](../-c-factory/index.md), but without argument.

### Parameters

`T` - The created type.

`createdType` - The type of objects created by this provider, *used for debug print only*.