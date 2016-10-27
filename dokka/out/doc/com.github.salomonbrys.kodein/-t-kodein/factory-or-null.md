[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [factoryOrNull](.)

# factoryOrNull

`@JvmOverloads fun factoryOrNull(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): (Any?) -> Any`

Gets a factory for the given argument type, return type and tag, or null if none is found.

### Parameters

`argType` - The type of argument the returned factory takes.

`type` - The type of object to retrieve with the returned factory.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A factory, or null if no factory was found.

`@JvmOverloads fun <T : Any> factoryOrNull(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): (Any?) -> T`
`@JvmOverloads fun <T : Any> factoryOrNull(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (Any?) -> T`

Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.

### Parameters

`T` - The type of object to retrieve with the returned factory.

`argType` - The type of argument the returned factory takes.

`type` - The type of object to retrieve with the returned factory.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A factory of `T`, or null if no factory was found.

`@JvmOverloads fun <A> factoryOrNull(argType: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<A>, type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): (A) -> Any`
`@JvmOverloads fun <A> factoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<A>, type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): (A) -> Any`

Gets a factory for the given argument type, return type and tag, or null if none is found.

### Parameters

`A` - The type of argument the returned factory takes.

`argType` - The type of argument the returned factory takes.

`type` - The type of object to retrieve with the returned factory.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A factory, or null if no factory was found.

`@JvmOverloads fun <A, T : Any> factoryOrNull(argType: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<A>, type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): (A) -> T`
`@JvmOverloads fun <A, T : Any> factoryOrNull(argType: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<A>, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`
`@JvmOverloads fun <A, T : Any> factoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<A>, type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): (A) -> T`
`@JvmOverloads fun <A, T : Any> factoryOrNull(argType: `[`TypeToken`](../-type-token/index.md)`<A>, type: `[`TypeToken`](../-type-token/index.md)`<T>, tag: Any? = null): (A) -> T`

Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.

### Parameters

`A` - The type of argument the returned factory takes.

`T` - The type of object to retrieve with the returned factory.

`argType` - The type of argument the returned factory takes.

`type` - The type of object to retrieve with the returned factory.

`tag` - The bound tag, if any.

### Exceptions

`Kodein.DependencyLoopException` - When calling the factory, if the value construction triggered a dependency loop.

**Return**
A factory of `T`, or null if no factory was found.

