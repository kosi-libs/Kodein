[com.github.salomonbrys.kodein](../index.md) / [Factory](.)

# Factory

`interface Factory<in A, out T : Any>`

Base class that knows how to get an instance.

All bindings are bound to a Factory.
Whether this factory creates a new instance at each call or not is left to implementation.

### Parameters

`A` - The type of argument used to create or retrieve an instance.

`T` - The type of instance this factory creates or retrieves.

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `abstract val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `abstract val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `abstract fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `abstract fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`<br>Get an instance of type `T` function argument `A`. |

### Inheritors

| Name | Summary |
|---|---|
| [CFactory](../-c-factory/index.md) | `class CFactory<in A, out T : Any> : Factory<A, T>`<br>Concrete factory: each time an instance is needed, the function [creator](../-c-factory/creator.md) function will be called. |
| [CMultiton](../-c-multiton/index.md) | `class CMultiton<in A, out T : Any> : Factory<A, T>`<br>Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument. |
| [CRefMultiton](../-c-ref-multiton/index.md) | `class CRefMultiton<in A, out T : Any> : Factory<A, T>`<br>Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference. |
| [CScopedSingleton](../-c-scoped-singleton/index.md) | `class CScopedSingleton<C, out T : Any> : `[`AScoped`](../-a-scoped/index.md)`<C, C, T>, Factory<C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
| [Provider](../-provider/index.md) | `interface Provider<out T : Any> : Factory<Unit, T>`<br>Factory specialization that has no argument. |
