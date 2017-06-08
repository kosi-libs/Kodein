[com.github.salomonbrys.kodein](../../../index.md) / [KodeinContainer](../../index.md) / [Builder](../index.md) / [BindBinder](index.md) / [with](.)

# with

`infix fun with(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, out T>): Unit`

Binds the previously given type &amp; tag to the given binding.

The bound type will be the [Binding.createdType](../../../../com.github.salomonbrys.kodein.bindings/-binding/created-type.md).

### Parameters

`binding` - The binding to bind.

### Exceptions

`Kodein.OverridingException` - If this bindings overrides an existing binding and is not allowed to.