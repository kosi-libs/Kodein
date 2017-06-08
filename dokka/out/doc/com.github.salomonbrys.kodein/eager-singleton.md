[com.github.salomonbrys.kodein](index.md) / [eagerSingleton](.)

# eagerSingleton

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.eagerSingleton(noinline creator: `[`NoArgBindingKodein`](../com.github.salomonbrys.kodein.bindings/-no-arg-binding-kodein/index.md)`.() -> T): `[`EagerSingletonBinding`](../com.github.salomonbrys.kodein.bindings/-eager-singleton-binding/index.md)`<T>`

Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.

T generics will be kept.

### Parameters

`T` - The created type.

`creator` - The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.

**Return**
An eager singleton ready to be bound.

