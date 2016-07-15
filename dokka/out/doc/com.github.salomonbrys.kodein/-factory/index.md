[com.github.salomonbrys.kodein](../index.md) / [Factory](.)

# Factory

`interface Factory<in A, out T : Any>`

Base class that knows how to get an instance.

All bindings are bound to a Factory.
Whether this factory creates a new instance at each call or not is left to implementation.

### Parameters

`A` - The type of argument used to create or retrieve an instance.

`T` - The type instance this factory creates or retrieves.

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `abstract val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `abstract val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](description.md) | `abstract val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [factoryName](factory-name.md) | `abstract val factoryName: String`<br>The name of this factory, *used for debug print only*. |
| [fullDescription](full-description.md) | `abstract val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `abstract fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Get an instance of type `T` function argument `A`. |

### Inheritors

| Name | Summary |
|---|---|
| [AFactory](../-a-factory/index.md) | `abstract class AFactory<in A, out T : Any> : Factory<A, T>`<br>Factory base. |
| [AProvider](../-a-provider/index.md) | `abstract class AProvider<out T : Any> : Factory<Unit, T>`<br>Provider base. |
| [AScoped](../-a-scoped/index.md) | `abstract class AScoped<in A, out C, out T : Any> : Factory<A, T>`<br>A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md). |
