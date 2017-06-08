[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [DirectBinder](index.md) / [from](.)

# from

`infix fun from(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, *>): Unit`

Binds the previously given tag to the given binding.

The bound type will be the [Binding.createdType](../../../../com.github.salomonbrys.kodein.bindings/-binding/created-type.md).

### Parameters

`binding` - The binding to bind.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.