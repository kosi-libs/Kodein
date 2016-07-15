[com.github.salomonbrys.kodein](../index.md) / [CEagerSingleton](.)

# CEagerSingleton

`class CEagerSingleton<out T : Any> : `[`ASingleton`](../-a-singleton/index.md)`<T>`

Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

### Parameters

`T` - The created type.

`createdType` - The type of the created object.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CEagerSingleton(builder: `[`Builder`](../-kodein/-builder/index.md)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, creator: `[`Kodein`](../-kodein/index.md)`.() -> T)`<br>Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance. |

### Inherited Properties

| Name | Summary |
|---|---|
| [creator](../-a-singleton/creator.md) | `val creator: `[`Kodein`](../-kodein/index.md)`.() -> T`<br>The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getInstance](../-a-singleton/get-instance.md) | `open fun getInstance(kodein: `[`Kodein`](../-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`): T`<br>Get an instance of type `T`. |
