[com.github.salomonbrys.kodein](../index.md) / [CThreadSingleton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CThreadSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`Kodein`](../-kodein/index.md)`.() -> T)`

Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.

### Parameters

`T` - The created type.

`createdType` - The type of the created objects, *used for debug print only*.