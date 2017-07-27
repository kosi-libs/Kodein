[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [TypeBinder](index.md) / [with](.)

# with

`infix fun with(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, out T>): Unit`

Binds the previously given type and tag to the given binding.

### Parameters

`binding` - The binding to bind.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.