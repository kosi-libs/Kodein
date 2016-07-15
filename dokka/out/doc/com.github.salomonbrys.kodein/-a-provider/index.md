[com.github.salomonbrys.kodein](../index.md) / [AProvider](.)

# AProvider

`abstract class AProvider<out T : Any> : `[`Factory`](../-factory/index.md)`<Unit, T>`

Provider base.

A provider is like a [AFactory](../-a-factory/index.md), but without argument (the [Factory](../-factory/index.md) is registered with a `Unit` argument).

### Parameters

`T` - The created type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AProvider(factoryName: String, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>Provider base. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [factoryName](factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>`abstract fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inheritors

| Name | Summary |
|---|---|
| [ASingleton](../-a-singleton/index.md) | `abstract class ASingleton<out T : Any> : AProvider<T>`<br>Singleton base: will create an instance on first request and will subsequently always return the same instance. |
| [CInstance](../-c-instance/index.md) | `class CInstance<out T : Any> : AProvider<T>`<br>Concrete instance provider: will always return the given instance. |
| [CProvider](../-c-provider/index.md) | `class CProvider<out T : Any> : AProvider<T>`<br>Concrete provider: each time an instance is needed, the function [creator](../-c-provider/creator.md) function will be called. |
| [CThreadSingleton](../-c-thread-singleton/index.md) | `class CThreadSingleton<out T : Any> : AProvider<T>`<br>Concrete thread singleton: will create an instance on first request per thread and will subsequently always return the same instance for this thread. |
