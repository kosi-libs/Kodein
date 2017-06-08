[com.github.salomonbrys.kodein](../../../index.md) / [KodeinContainer](../../index.md) / [Builder](../index.md) / [BindBinder](.)

# BindBinder

`inner class BindBinder<T : Any>`

Left part of the bind-binding syntax (`bind(Kodein.Bind(type, tag))`).

### Properties

| Name | Summary |
|---|---|
| [bind](bind.md) | `val bind: `[`Bind`](../../../-kodein/-bind/index.md)`<T>`<br>The type and tag object that will compose the key to bind. |
| [overrides](overrides.md) | `val overrides: Boolean?`<br>`true` if it must override, `false` if it must not, `null` if it can but is not required to. |

### Functions

| Name | Summary |
|---|---|
| [with](with.md) | `infix fun with(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, out T>): Unit`<br>Binds the previously given type &amp; tag to the given binding. |
