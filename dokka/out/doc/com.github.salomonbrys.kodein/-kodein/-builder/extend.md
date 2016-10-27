[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [extend](.)

# extend

`fun extend(kodein: `[`Kodein`](../index.md)`, allowOverride: Boolean = false): Unit`

Imports all bindings defined in the given [Kodein](../index.md) into this builder.

Note that this preserves scopes, meaning that a singleton-bound in the kodein argument will continue to exist only once.
Both kodein objects will share the same instance.

### Parameters

`kodein` - The kodein object to import.

`allowOverride` - Whether this module is allowed to override existing bindings.
If it is not, overrides (even explicit) will throw an [OverridingException](../-overriding-exception/index.md).

### Exceptions

`OverridingException` - If this kodein overrides an existing binding and is not allowed to
OR [allowOverride](extend.md#com.github.salomonbrys.kodein.Kodein.Builder$extend(com.github.salomonbrys.kodein.Kodein, kotlin.Boolean)/allowOverride) is true while YOU dont have the permission to override.