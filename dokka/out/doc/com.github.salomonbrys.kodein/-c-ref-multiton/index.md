[com.github.salomonbrys.kodein](../index.md) / [CRefMultiton](.)

# CRefMultiton

`class CRefMultiton<in A, out T : Any> : `[`Factory`](../-factory/index.md)`<A, T>`

Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference.

### Parameters

`T` - The type of the instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CRefMultiton(argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, refMaker: `[`RefMaker`](../-ref-maker/index.md)`, creator: `[`FactoryKodein`](../-factory-kodein/index.md)`.(A) -> T)`<br>Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference. |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument, *used for debug print only*. |
| [createdType](created-type.md) | `val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the created object, *used for debug print only*. |
| [creator](creator.md) | `val creator: `[`FactoryKodein`](../-factory-kodein/index.md)`.(A) -> T`<br>A function that should always create a new object. |
| [refMaker](ref-maker.md) | `val refMaker: `[`RefMaker`](../-ref-maker/index.md)<br>Reference Maker that defines the kind of reference being used. |

### Inherited Properties

| Name | Summary |
|---|---|
| [description](../-factory/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-factory/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Get an instance of type `T` function argument `A`. |
