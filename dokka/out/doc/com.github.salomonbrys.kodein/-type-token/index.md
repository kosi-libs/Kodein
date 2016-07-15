[com.github.salomonbrys.kodein](../index.md) / [TypeToken](.)

# TypeToken

`interface TypeToken<T>`

An interface that contains a simple [Type](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html) but is parameterized to enable type safety.

### Parameters

`T` - The type represented by the [type](type.md) object.

### Properties

| Name | Summary |
|---|---|
| [type](type.md) | `abstract val type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>This type **should** reflect the type `T`. |

### Inheritors

| Name | Summary |
|---|---|
| [TypeReference](../-type-reference/index.md) | `abstract class TypeReference<T> : TypeToken<T>`<br>Class used to get a generic type at runtime. |
