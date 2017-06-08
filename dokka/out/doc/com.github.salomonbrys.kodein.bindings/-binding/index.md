[com.github.salomonbrys.kodein.bindings](../index.md) / [Binding](.)

# Binding

`interface Binding<in A, T : Any>`

Base class that knows how to get an instance.

All bindings are bound to a Binding.
Whether this factory creates a new instance at each call or not is left to implementation.

### Parameters

`A` - The type of argument used to create or retrieve an instance.

`T` - The type of instance this factory creates or retrieves.

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `abstract val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>`<br>The type of the argument this factory will function for. |
| [createdType](created-type.md) | `abstract val createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>`<br>The type of object that is created by this factory. |
| [description](description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [factoryFullName](factory-full-name.md) | `open fun factoryFullName(): String`<br>The full(er) name of this factory, *used for debug print only*. |
| [factoryName](factory-name.md) | `abstract fun factoryName(): String`<br>The name of this factory, *used for debug print only*. |
| [getInstance](get-instance.md) | `abstract fun getInstance(kodein: `[`BindingKodein`](../-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, T>, arg: A): T`<br>Get an instance of type `T` function argument `A`. |

### Inheritors

| Name | Summary |
|---|---|
| [FactoryBinding](../-factory-binding/index.md) | `class FactoryBinding<A, T : Any> : Binding<A, T>`<br>Concrete factory: each time an instance is needed, the function [creator](../-factory-binding/creator.md) function will be called. |
| [MultitonBinding](../-multiton-binding/index.md) | `class MultitonBinding<A, T : Any> : Binding<A, T>`<br>Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument. |
| [NoArgBinding](../-no-arg-binding/index.md) | `interface NoArgBinding<T : Any> : Binding<Unit, T>`<br>Binding specialization that has no argument. |
| [RefMultitonBinding](../../com.github.salomonbrys.kodein/-ref-multiton-binding/index.md) | `class RefMultitonBinding<A, T : Any> : Binding<A, T>`<br>Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference. |
| [ScopedSingletonBinding](../-scoped-singleton-binding/index.md) | `class ScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](../-a-scoped-binding/index.md)`<C, C, T>, Binding<C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
