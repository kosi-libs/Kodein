[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Bind](.)

# Bind

`data class Bind<out T : Any>`

Part of a [Key](../-key/index.md) that represents the left part of a bind declaration.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Bind(type: `[`TypeToken`](../../-type-token/index.md)`<out T>, tag: Any?)`<br>Part of a [Key](../-key/index.md) that represents the left part of a bind declaration. |

### Properties

| Name | Summary |
|---|---|
| [description](description.md) | `val description: String`<br>Description using simple type names. The description is as close as possible to the code used to create this bind. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>Description using full type names. The description is as close as possible to the code used to create this bind. |
| [tag](tag.md) | `val tag: Any?`<br>The optional tag. |
| [type](type.md) | `val type: `[`TypeToken`](../../-type-token/index.md)`<out T>`<br>The type that is bound. |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): String` |
