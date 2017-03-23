[com.github.salomonbrys.kodein](../index.md) / [Provider](.)

# Provider

`interface Provider<out T : Any> : `[`Factory`](../-factory/index.md)`<Unit, T>`

[Factory](../-factory/index.md) specialization that has no argument.

As a factory does need an argument, it uses `Unit` as its argument.

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `open val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of the argument this factory will function for. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Inherited Properties

| Name | Summary |
|---|---|
| [createdType](../-factory/created-type.md) | `abstract val createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of object that is created by this factory. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`FactoryKodein`](../-factory-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: Unit): T`<br>`abstract fun getInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [factoryFullName](../-factory/factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](../-factory/factory-name.md) | `abstract fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |

### Inheritors

| Name | Summary |
|---|---|
| [ASingleton](../-a-singleton/index.md) | `abstract class ASingleton<out T : Any> : Provider<T>`<br>Singleton base: will create an instance on first request and will subsequently always return the same instance. |
| [CAutoScopedSingleton](../-c-auto-scoped-singleton/index.md) | `class CAutoScopedSingleton<out C, out T : Any> : `[`AScoped`](../-a-scoped/index.md)`<Unit, C, T>, Provider<T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [CInstance](../-c-instance/index.md) | `class CInstance<out T : Any> : Provider<T>`<br>Concrete instance provider: will always return the given instance. |
| [CProvider](../-c-provider/index.md) | `class CProvider<out T : Any> : Provider<T>`<br>Concrete provider: each time an instance is needed, the function [creator](../-c-provider/creator.md) function will be called. |
| [CRefSingleton](../-c-ref-singleton/index.md) | `class CRefSingleton<out T : Any> : Provider<T>`<br>Concrete referenced singleton provider: will always return the instance managed by the given reference. |
