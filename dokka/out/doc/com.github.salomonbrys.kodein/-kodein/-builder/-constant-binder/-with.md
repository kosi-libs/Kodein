[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [ConstantBinder](index.md) / [With](.)

# With

`fun <T : Any> With(valueType: `[`TypeToken`](../../../-type-token/index.md)`<T>, value: T): Unit`

Binds the previously given tag to the given instance.

### Parameters

`T` - The type of value to bind.

`value` - The instance to bind.

`valueType` - The type to bind the instance to.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.