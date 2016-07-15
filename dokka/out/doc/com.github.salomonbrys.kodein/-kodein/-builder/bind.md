[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [bind](.)

# bind

`inline fun <reified T : Any> bind(tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-t-builder/-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

### Parameters

`T` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [TBuilder.TypeBinder.with](-t-builder/-type-binder/with.md)) on it to finish the binding syntax and register the binding.

`fun bind(tag: Any? = null, overrides: Boolean? = null): `[`DirectBinder`](-t-builder/-direct-binder/index.md)

Starts a direct binding with a given tag. A direct bind does not define the type to be binded, the type will be defined according to the bound factory.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [TBuilder.DirectBinder.from](-t-builder/-direct-binder/from.md)) on it to finish the binding syntax and register the binding.

