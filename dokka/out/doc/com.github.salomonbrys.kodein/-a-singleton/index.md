[com.github.salomonbrys.kodein](../index.md) / [ASingleton](.)

# ASingleton

`abstract class ASingleton<out T : Any> : `[`AProvider`](../-a-provider/index.md)`<T>`

Singleton base: will create an instance on first request and will subsequently always return the same instance.

### Parameters

`T` - The created type.

`factoryName` - The name of this singleton factory, *used for debug print only*.

`createdType` - The type of the created object, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ASingleton(factoryName: String, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`<br>Singleton base: will create an instance on first request and will subsequently always return the same instance. |

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T`<br>The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance. |

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
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-a-provider/get-instance.md) | `fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |

### Inheritors

| Name | Summary |
|---|---|
| [CEagerSingleton](../-c-eager-singleton/index.md) | `class CEagerSingleton<out T : Any> : ASingleton<T>`<br>Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance. |
| [CSingleton](../-c-singleton/index.md) | `class CSingleton<out T : Any> : ASingleton<T>`<br>Concrete singleton: will create an instance on first request and will subsequently always return the same instance. |
