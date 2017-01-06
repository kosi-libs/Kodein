[com.github.salomonbrys.kodein](../index.md) / [CInstance](.)

# CInstance

`class CInstance<out T : Any> : `[`AProvider`](../-a-provider/index.md)`<T>`

Concrete instance provider: will always return the given instance.

### Parameters

`T` - The type of the instance.

`instanceType` - The type of the object, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CInstance(instanceType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, instance: T)`<br>Concrete instance provider: will always return the given instance. |

### Properties

| Name | Summary |
|---|---|
| [description](description.md) | `val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |
| [instance](instance.md) | `val instance: T`<br>The object that will always be returned. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-a-provider/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](../-a-provider/created-type.md) | `open val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [factoryName](../-a-provider/factory-name.md) | `open val factoryName: String`<br>The name of this factory, *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-a-provider/get-instance.md) | `fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |
