[com.github.salomonbrys.kodein.erased](index.md) / [bind](.)

# bind

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.bind(tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](../com.github.salomonbrys.kodein/-kodein/-builder/-t-builder/-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

T generics will be erased!

### Parameters

`T` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [Kodein.Builder.TBuilder.TypeBinder.with](../com.github.salomonbrys.kodein/-kodein/-builder/-t-builder/-type-binder/with.md)) on it to finish the binding syntax and register the binding.

