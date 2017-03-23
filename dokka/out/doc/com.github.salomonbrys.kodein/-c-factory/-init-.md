[com.github.salomonbrys.kodein](../index.md) / [CFactory](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CFactory(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`FactoryKodein`](../-factory-kodein/index.md)`.(A) -> T)`

Concrete factory: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.CFactory$<init>(java.lang.reflect.Type, java.lang.reflect.Type, kotlin.Function2((com.github.salomonbrys.kodein.FactoryKodein, com.github.salomonbrys.kodein.CFactory.A, com.github.salomonbrys.kodein.CFactory.T)))/creator) function will be called.

### Parameters

`A` - The argument type.

`T` - The created type.

`argType` - The type of the argument used by this factory.

`createdType` - The type of objects created by this factory.