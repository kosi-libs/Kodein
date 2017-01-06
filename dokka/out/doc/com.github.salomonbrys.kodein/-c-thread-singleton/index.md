[com.github.salomonbrys.kodein](../index.md) / [CThreadSingleton](.)

# CThreadSingleton

`class CThreadSingleton<out T : Any> : `[`AProvider`](../-a-provider/index.md)`<T>`

Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread.

### Parameters

`T` - The created type.

`createdType` - The type of the created objects, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CThreadSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`<br>Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread. |

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T`<br>The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-a-provider/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](../-a-provider/created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](../-a-provider/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [factoryName](../-a-provider/factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |
| [fullDescription](../-a-provider/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-a-provider/get-instance.md) | `fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |
