[com.github.salomonbrys.kodein](index.md) / [bind](.)

# bind

`inline fun <reified T : Any> `[`Builder`](-kodein/-builder/index.md)`.bind(tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-kodein/-builder/-t-builder/-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

T generics will be kept.

### Parameters

`T` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must**, **may** or **must not** override an existing binding.

**Return**
The binder: call [Kodein.Builder.TBuilder.TypeBinder.with](-kodein/-builder/-t-builder/-type-binder/with.md)) on it to finish the binding syntax and register the binding.

