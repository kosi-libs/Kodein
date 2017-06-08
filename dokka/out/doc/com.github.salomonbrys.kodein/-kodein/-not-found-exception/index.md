[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [NotFoundException](.)

# NotFoundException

`class NotFoundException : RuntimeException`

Exception thrown when asked for a dependency that cannot be found.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `NotFoundException(key: `[`Key`](../-key/index.md)`<*, *>, message: String)`<br>Exception thrown when asked for a dependency that cannot be found. |

### Properties

| Name | Summary |
|---|---|
| [key](key.md) | `val key: `[`Key`](../-key/index.md)`<*, *>`<br>The key that was not found. |
