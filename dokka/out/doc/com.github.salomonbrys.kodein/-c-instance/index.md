[com.github.salomonbrys.kodein](../index.md) / [CInstance](.)

# CInstance

`class CInstance<out T : Any> : `[`Provider`](../-provider/index.md)`<T>`

Concrete instance provider: will always return the given instance.

### Parameters

`T` - The type of the instance.

`createdType` - The type of the object, *used for debug print only*.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CInstance(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, instance: T)`<br>Concrete instance provider: will always return the given instance. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](description.md) | `val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |
| [instance](instance.md) | `val instance: T`<br>The object that will always be returned. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-provider/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |

### Functions

| Name | Summary |
|---|---|
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-provider/get-instance.md) | `open fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |
