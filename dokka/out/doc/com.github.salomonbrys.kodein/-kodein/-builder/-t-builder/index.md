[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [TBuilder](.)

# TBuilder

`inner class TBuilder`

Class holding all typed API (meaning the API where you provide [Type](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html), [TypeToken](../../../-type-token/index.md) or `Class` objects).

### Types

| Name | Summary |
|---|---|
| [ConstantBinder](-constant-binder/index.md) | `inner class ConstantBinder`<br>Left part of the constant-binding syntax (`constant(tag)`). |
| [DirectBinder](-direct-binder/index.md) | `inner class DirectBinder`<br>Left part of the direct-binding syntax (`bind(tag)`). |
| [TypeBinder](-type-binder/index.md) | `inner class TypeBinder<in T : Any>`<br>Left part of the type-binding syntax (`bind(type, tag)`). |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TBuilder()`<br>Class holding all typed API (meaning the API where you provide [Type](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html), [TypeToken](../../../-type-token/index.md) or `Class` objects). |

### Functions

| Name | Summary |
|---|---|
| [bind](bind.md) | `fun bind(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<Any>`<br>`fun <T : Any> bind(type: `[`TypeToken`](../../../-type-token/index.md)`<T>, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<T>`<br>`fun <T : Any> bind(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null, overrides: Boolean? = null): `[`TypeBinder`](-type-binder/index.md)`<T>`<br>Starts the binding of a given type with a given tag.`fun bind(tag: Any? = null, overrides: Boolean? = null): `[`DirectBinder`](-direct-binder/index.md)<br>Starts a direct binding with a given tag. A direct bind does not define the type to be binded, the type will be defined according to the bound factory. |
| [constant](constant.md) | `fun constant(tag: Any, overrides: Boolean? = null): `[`ConstantBinder`](-constant-binder/index.md)<br>Starts a constant binding. |
