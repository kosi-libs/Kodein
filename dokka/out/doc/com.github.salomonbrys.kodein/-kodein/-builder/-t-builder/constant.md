[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [TBuilder](index.md) / [constant](.)

# constant

`fun constant(tag: Any, overrides: Boolean? = null): `[`ConstantBinder`](-constant-binder/index.md)

Starts a constant binding.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must** or **must not** override an existing binding.

**Return**
The binder: call `with` on it to finish the binding syntax and register the binding.

