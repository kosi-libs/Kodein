[com.github.salomonbrys.kodein](../../../../index.md) / [Kodein](../../../index.md) / [Builder](../../index.md) / [TBuilder](../index.md) / [ConstantBinder](index.md) / [with](.)

# with

`fun with(value: Any, valueType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`): Unit`

Binds the previously given tag to the given instance.

### Parameters

`value` - The instance to bind.

`valueType` - The type to bind the instance to.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.

`fun <T> with(value: T, valueType: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>): Unit`
`fun <T> with(value: T, valueType: `[`TypeToken`](../../../../-type-token/index.md)`<T>): Unit`

Binds the previously given tag to the given instance.

### Parameters

`T` - The type of value to bind.

`value` - The instance to bind.

`valueType` - The type to bind the instance to.

### Exceptions

`OverridingException` - If this bindings overrides an existing binding and is not allowed to.