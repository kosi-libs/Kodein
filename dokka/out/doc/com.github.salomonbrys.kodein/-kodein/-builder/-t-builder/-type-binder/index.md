[com.github.salomonbrys.kodein](../../../../index.md) / [Kodein](../../../index.md) / [Builder](../../index.md) / [TBuilder](../index.md) / [TypeBinder](.)

# TypeBinder

`inner class TypeBinder<in T : Any>`

Left part of the type-binding syntax (`bind(type, tag)`).

### Parameters

`T` - The type to bind.

### Functions

| Name | Summary |
|---|---|
| [with](with.md) | `infix fun <R : T> with(factory: `[`Factory`](../../../../-factory/index.md)`<*, R>): Unit`<br>Binds the previously given type and tag to the given factory. |
