[com.github.salomonbrys.kodein](../../../index.md) / [KodeinContainer](../../index.md) / [Builder](../index.md) / [KeyBinder](.)

# KeyBinder

`inner class KeyBinder<A, T : Any>`

Left part of the key-binding syntax (`bind(Kodein.Key(Kodein.Bind(type, tag), argType))`).

### Properties

| Name | Summary |
|---|---|
| [key](key.md) | `val key: `[`Key`](../../../-kodein/-key/index.md)`<A, T>`<br>The key to bind. |

### Functions

| Name | Summary |
|---|---|
| [with](with.md) | `infix fun with(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<A, T>): Unit`<br>Binds the previously given key to the given binding. |
