[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [TBuilder](index.md) / [bind](.)

# bind

`fun bind(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<Any>`

Starts the binding of a given type with a given tag.

### Parameters

`type` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must** or **must not** override an existing binding.

**Return**
The binder: call [TypeBinder.with](-type-binder/with.md)) on it to finish the binding syntax and register the binding.

`fun <T : Any> bind(type: `[`TypeToken`](../../../-type-token/index.md)`<T>, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<T>`
`fun <T : Any> bind(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<T>`

Starts the binding of a given type with a given tag.

### Parameters

`T` - The type of value to bind.

`type` - The type to bind.

`tag` - The tag to bind.

`overrides` - Whether this bind **must** or **must not** override an existing binding.

**Return**
The binder: call [TypeBinder.with](-type-binder/with.md)) on it to finish the binding syntax and register the binding.

`fun bind(tag: Any? = null, overrides: Boolean? = null): `[`DirectBinder`](-direct-binder/index.md)

Starts a direct binding with a given tag. A direct bind does not define the type to be binded, the type will be defined according to the bound factory.

### Parameters

`tag` - The tag to bind.

`overrides` - Whether this bind **must** or **must not** override an existing binding.

**Return**
The binder: call [DirectBinder.from](-direct-binder/from.md)) on it to finish the binding syntax and register the binding.

