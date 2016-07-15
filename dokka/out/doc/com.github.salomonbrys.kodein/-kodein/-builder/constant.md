[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [constant](.)

# constant

`fun constant(tag: Any, overrides: Boolean? = null): `[`ConstantBinder`](-constant-binder/index.md)

Starts a constant binding.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [ConstantBinder.with](-constant-binder/with.md)) on it to finish the binding syntax and register the binding.

