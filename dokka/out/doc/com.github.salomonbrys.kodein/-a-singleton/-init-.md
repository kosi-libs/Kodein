[com.github.salomonbrys.kodein](../index.md) / [ASingleton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`ASingleton(factoryName: String, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`

Singleton base: will create an instance on first request and will subsequently always return the same instance.

### Parameters

`T` - The created type.

`factoryName` - The name of this singleton factory, *used for debug print only*.

`createdType` - The type of the created object, *used for debug print only*.