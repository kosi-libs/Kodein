[com.github.salomonbrys.kodein.bindings](../index.md) / [ASingleton](.)

# ASingleton

`abstract class ASingleton<T : Any> : `[`NoArgBinding`](../-no-arg-binding/index.md)`<T>`

SingletonBinding base: will create an instance on first request and will subsequently always return the same instance.

### Parameters

`T` - The created type.

### Properties

| Name | Summary |
|---|---|
| [creator](creator.md) | `val creator: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`.() -> T`<br>The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [argType](../-no-arg-binding/arg-type.md) | `open val argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<Unit>`<br>The type of the argument this factory will function for. |
| [description](../-no-arg-binding/description.md) | `open val description: String`<br>The description of this factory (using simple type names), *used for debug print only*. |
| [fullDescription](../-no-arg-binding/full-description.md) | `open val fullDescription: String`<br>The description of this factory (using full type names), *used for debug print only*. |

### Functions

| Name | Summary |
|---|---|
| [getInstance](get-instance.md) | `open fun getInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<Unit, T>): T`<br>Get an instance of type `T`. |

### Inheritors

| Name | Summary |
|---|---|
| [EagerSingletonBinding](../-eager-singleton-binding/index.md) | `class EagerSingletonBinding<T : Any> : ASingleton<T>`<br>Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance. |
| [SingletonBinding](../-singleton-binding/index.md) | `class SingletonBinding<T : Any> : ASingleton<T>`<br>Concrete singleton: will create an instance on first request and will subsequently always return the same instance. |
