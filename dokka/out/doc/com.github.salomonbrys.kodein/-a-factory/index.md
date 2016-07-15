[com.github.salomonbrys.kodein](../index.md) / [AFactory](.)

# AFactory

`abstract class AFactory<in A, out T : Any> : `[`Factory`](../-factory/index.md)`<A, T>`

Factory base.

Enables sub-classes to implement only [Factory.getInstance](../-factory/get-instance.md).

### Parameters

`A` - The factory argument type.

`T` - The created type.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `AFactory(factoryName: String, argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>Factory base. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [factoryName](factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Inheritors

| Name | Summary |
|---|---|
| [CFactory](../-c-factory/index.md) | `class CFactory<in A, out T : Any> : AFactory<A, T>`<br>Concrete factory: each time an instance is needed, the function [creator](../-c-factory/creator.md) function will be called. |
