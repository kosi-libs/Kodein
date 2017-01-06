[com.github.salomonbrys.kodein](index.md) / [bind](.)

# bind

`inline fun `[`Builder`](-kodein/-builder/index.md)`.bind(tag: Any? = null, overrides: Boolean? = null): `[`DirectBinder`](-kodein/-builder/-t-builder/-direct-binder/index.md)

Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [Kodein.Builder.TBuilder.DirectBinder.from](-kodein/-builder/-t-builder/-direct-binder/from.md)) on it to finish the binding syntax and register the binding.

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.bind(tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-kodein/-builder/-t-builder/-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

T generics will be kept.

### Parameters

`T` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [Kodein.Builder.TBuilder.TypeBinder.with](-kodein/-builder/-t-builder/-type-binder/with.md)) on it to finish the binding syntax and register the binding.

