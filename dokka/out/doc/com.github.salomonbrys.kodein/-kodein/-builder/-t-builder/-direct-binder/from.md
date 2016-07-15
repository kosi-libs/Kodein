[com.github.salomonbrys.kodein](../../../../index.md) / [Kodein](../../../index.md) / [Builder](../../index.md) / [TBuilder](../index.md) / [DirectBinder](index.md) / [from](.)

# from

`infix fun from(factory: `[`Factory`](../../../../-factory/index.md)`<*, *>): Unit`

Binds the previously given tag to the given factory.

The bound type will be the [Factory.createdType](../../../../-factory/created-type.md).

### Parameters

`factory` - The factory to bind.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.