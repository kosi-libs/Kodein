[com.github.salomonbrys.kodein](../index.md) / [RefMaker](.)

# RefMaker

`interface RefMaker`

A Function that creates a reference.

### Functions

| Name | Summary |
|---|---|
| [make](make.md) | `abstract fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`<br>A Function that creates a reference. |

### Inheritors

| Name | Summary |
|---|---|
| [softReference](../soft-reference/index.md) | `object softReference : RefMaker`<br>Soft Reference Maker. |
| [threadLocal](../thread-local/index.md) | `object threadLocal : RefMaker`<br>Thread Local Reference Maker. |
| [weakReference](../weak-reference/index.md) | `object weakReference : RefMaker`<br>Weak Reference Maker. |
