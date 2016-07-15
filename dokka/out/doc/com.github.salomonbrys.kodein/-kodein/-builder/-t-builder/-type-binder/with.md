[com.github.salomonbrys.kodein](../../../../index.md) / [Kodein](../../../index.md) / [Builder](../../index.md) / [TBuilder](../index.md) / [TypeBinder](index.md) / [with](.)

# with

`infix fun <R : T> with(factory: `[`Factory`](../../../../-factory/index.md)`<*, R>): Unit`

Binds the previously given type and tag to the given factory.

### Parameters

`R` - The real type the factory will return.

`factory` - The factory to bind.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.