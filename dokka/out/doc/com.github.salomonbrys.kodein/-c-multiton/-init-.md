[com.github.salomonbrys.kodein](../index.md) / [CMultiton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CMultiton(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`FactoryKodein`](../-factory-kodein/index.md)`.(A) -> T)`

Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.

### Parameters

`T` - The created type.