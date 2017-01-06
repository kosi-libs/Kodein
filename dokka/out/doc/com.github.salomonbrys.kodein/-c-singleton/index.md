[com.github.salomonbrys.kodein](../index.md) / [CSingleton](.)

# CSingleton

`class CSingleton<out T : Any> : `[`ASingleton`](../-a-singleton/index.md)`<T>`

Concrete singleton: will create an instance on first request and will subsequently always return the same instance.

### Parameters

`T` - The created type.

`createdType` - The type of the created object, *used for debug print only*.

`creator` - The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`<br>Concrete singleton: will create an instance on first request and will subsequently always return the same instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [creator](../-a-singleton/creator.md) | `val creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T`<br>The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-a-singleton/get-instance.md) | `open fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |
