[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [ConstantBinder](.)

# ConstantBinder

`inner class ConstantBinder`

Left part of the constant-binding syntax (`constant(tag)`).

### Properties

| Name | Summary |
|---|---|
| [binder](binder.md) | `val binder: `[`ConstantBinder`](../-t-builder/-constant-binder/index.md)<br>The typed binder to use to actually bind. |

### Functions

| Name | Summary |
|---|---|
| [withErased](with-erased.md) | `infix fun <T : Any> withErased(value: T): Unit`<br>Binds the previously given tag to the given instance. |
| [withGeneric](with-generic.md) | `infix fun <T : Any> withGeneric(value: T): Unit`<br>Binds the previously given tag to the given instance. |

### Extension Functions

| Name | Summary |
|---|---|
| [with](../../../with.md) | `infix fun <T : Any> ConstantBinder.with(value: T): Unit`<br>Binds the previously given tag to the given instance. |
| [with](../../../../com.github.salomonbrys.kodein.erased/with.md) | `infix fun <T : Any> ConstantBinder.with(value: T): Unit`<br>Binds the previously given tag to the given instance. |
