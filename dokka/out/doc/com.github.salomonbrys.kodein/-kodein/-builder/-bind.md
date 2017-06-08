[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [Bind](.)

# Bind

`fun <T : Any> Bind(type: `[`TypeToken`](../../-type-token/index.md)`<T>, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

### Parameters

`T` - The type of value to bind.

`type` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must** or **must not** override an existing binding.

**Return**
The binder: call [TypeBinder.with](-type-binder/with.md)) on it to finish the binding syntax and register the binding.

