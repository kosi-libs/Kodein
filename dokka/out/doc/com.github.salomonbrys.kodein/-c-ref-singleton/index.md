[com.github.salomonbrys.kodein](../index.md) / [CRefSingleton](.)

# CRefSingleton

`class CRefSingleton<out T : Any> : `[`Provider`](../-provider/index.md)`<T>`

Concrete referenced singleton provider: will always return the instance managed by the given reference.

### Parameters

`T` - The type of the instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CRefSingleton(createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, refMaker: `[`RefMaker`](../-ref-maker/index.md)`, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T)`<br>Concrete referenced singleton provider: will always return the instance managed by the given reference. |

### Properties

| Name | Summary |
|---|---|
| [createdType](created-type.md) | `val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [creator](creator.md) | `val creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T`<br>A function that should always create a new object. |
| [refMaker](ref-maker.md) | `val refMaker: `[`RefMaker`](../-ref-maker/index.md)<br>Reference Maker that defines the kind of reference being used. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-provider/arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [description](../-provider/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-provider/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-provider/get-instance.md) | `open fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>Get an instance of type `T`. |
