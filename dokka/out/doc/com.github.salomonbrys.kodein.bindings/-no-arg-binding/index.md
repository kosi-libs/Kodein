[com.github.salomonbrys.kodein.bindings](../index.md) / [NoArgBinding](.)

# NoArgBinding

`interface NoArgBinding<T : Any> : `[`Binding`](../-binding/index.md)`<Unit, T>`

[Binding](../-binding/index.md) specialization that has no argument.

As a factory does need an argument, it uses `Unit` as its argument.

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `open val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<Unit>`<br>The type of the argument this factory will function for. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Inherited Properties

| Name | Summary |
|---|---|
| [createdType](../-binding/created-type.md) | `abstract val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>, arg: Unit): T`<br>`abstract fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |

### Inherited Functions

| Name | Summary |
|---|---|
| [factoryFullName](../-binding/factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](../-binding/factory-name.md) | `abstract fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |

### Inheritors

| Name | Summary |
|---|---|
| [ASingleton](../-a-singleton/index.md) | `abstract class ASingleton<T : Any> : NoArgBinding<T>`<br>SingletonBinding base: will create an instance on first request and will subsequently always return the same instance. |
| [AutoScopedSingletonBinding](../-auto-scoped-singleton-binding/index.md) | `class AutoScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](../-a-scoped-binding/index.md)`<Unit, C, T>, NoArgBinding<T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [InstanceBinding](../-instance-binding/index.md) | `class InstanceBinding<T : Any> : NoArgBinding<T>`<br>Concrete instance provider: will always return the given instance. |
| [ProviderBinding](../-provider-binding/index.md) | `class ProviderBinding<T : Any> : NoArgBinding<T>`<br>Concrete provider: each time an instance is needed, the function [creator](../-provider-binding/creator.md) function will be called. |
| [RefSingletonBinding](../../com.github.salomonbrys.kodein/-ref-singleton-binding/index.md) | `class RefSingletonBinding<T : Any> : NoArgBinding<T>`<br>Concrete referenced singleton provider: will always return the instance managed by the given reference. |
| [SetBinding](../-set-binding/index.md) | `class SetBinding<T : Any> : NoArgBinding<Set<T>>, `[`BaseMultiBinding`](../-base-multi-binding/index.md)`<Unit, T, Set<T>>`<br>Binding that holds multiple factory bindings (e.g. with argument) in a set. |
