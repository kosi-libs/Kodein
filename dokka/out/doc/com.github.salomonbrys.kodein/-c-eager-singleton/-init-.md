[com.github.salomonbrys.kodein](../index.md) / [CEagerSingleton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CEagerSingleton(builder: `[`Builder`](../-kodein/-builder/index.md)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`Kodein`](../-kodein/index.md)`.() -> T)`

Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

### Parameters

`T` - The created type.

`createdType` - The type of the created object.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.