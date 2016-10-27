[com.github.salomonbrys.kodein](../index.md) / [CFactory](.)

# CFactory

`class CFactory<in A, out T : Any> : `[`AFactory`](../-a-factory/index.md)`<A, T>`

Concrete factory: each time an instance is needed, the function [creator](creator.md) function will be called.

### Parameters

`A` - The argument type.

`T` - The created type.

`argType` - The type object of the argument used by this factory.

`createdType` - The type of objects created by this factory.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CFactory(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`Kodein`](../-kodein/index.md)`.(A) -> T)`<br>Concrete factory: each time an instance is needed, the function [creator](-init-.md#com.github.salomonbrys.kodein.CFactory$<init>(java.lang.reflect.Type, java.lang.reflect.Type, kotlin.Function2((com.github.salomonbrys.kodein.Kodein, com.github.salomonbrys.kodein.CFactory.A, com.github.salomonbrys.kodein.CFactory.T)))/creator) function will be called. |

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: `[`Kodein`](../-kodein/index.md)`.(A) -> T`<br>The function that will be called each time an instance is requested. Should create a new instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-a-factory/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](../-a-factory/created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](../-a-factory/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [factoryName](../-a-factory/factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |
| [fullDescription](../-a-factory/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Get an instance of type `T` function argument `A`. |
