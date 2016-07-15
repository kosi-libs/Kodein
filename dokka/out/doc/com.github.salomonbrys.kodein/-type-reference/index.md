[com.github.salomonbrys.kodein](../index.md) / [TypeReference](.)

# TypeReference

`abstract class TypeReference<T> : `[`TypeToken`](../-type-token/index.md)`<T>`

Class used to get a generic type at runtime.

### Parameters

`T` - The type to extract.

**See Also**

[typeToken](../type-token.md)

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `TypeReference()`<br>Class used to get a generic type at runtime. |

### Properties

| Name | Summary |
|---|---|
| [trueType](true-type.md) | `val trueType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>Generic type, unwrapped. |
| [type](type.md) | `open val type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>This type **should** reflect the type `T`. |
