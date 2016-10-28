[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [bindDirect](.)

# bindDirect

`fun bindDirect(tag: Any? = null, overrides: Boolean? = null): `[`DirectBinder`](-t-builder/-direct-binder/index.md)

Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [TBuilder.DirectBinder.from](-t-builder/-direct-binder/from.md)) on it to finish the binding syntax and register the binding.

