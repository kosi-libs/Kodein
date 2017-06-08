[com.github.salomonbrys.kodein](../../../index.md) / [KodeinContainer](../../index.md) / [Builder](../index.md) / [KeyBinder](index.md) / [key](.)

# key

`val key: `[`Key`](../../../-kodein/-key/index.md)`<A, T>`

The key to bind.

### Property

`key` - The key to bind.

### Parameters

`overrides` - `true` if it must override, `false` if it must not, `null` if it can but is not required to.

### Exceptions

`Kodein.OverridingException` - If this bindings overrides an existing binding and is not allowed to.