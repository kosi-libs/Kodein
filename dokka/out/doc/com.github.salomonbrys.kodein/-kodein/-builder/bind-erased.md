[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [bindErased](.)

# bindErased

`inline fun <reified T : Any> bindErased(tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-t-builder/-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

T generics will be erased!

### Parameters

`T` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [TBuilder.TypeBinder.with](-t-builder/-type-binder/with.md)) on it to finish the binding syntax and register the binding.

