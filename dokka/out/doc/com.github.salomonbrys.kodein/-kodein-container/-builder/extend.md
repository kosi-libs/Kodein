[com.github.salomonbrys.kodein](../../index.md) / [KodeinContainer](../index.md) / [Builder](index.md) / [extend](.)

# extend

`fun extend(container: `[`KodeinContainer`](../index.md)`, allowOverride: Boolean = false): Unit`

Imports all bindings defined in the given [KodeinContainer](../index.md) into this builder.

Note that this preserves scopes, meaning that a singleton-binded in the container argument will continue to exist only once.
Both containers will share the same instance.

### Parameters

`container` - The container object to import.

`allowOverride` - Whether this module is allowed to override existing bindings.
If it is not, overrides (even explicit) will throw an [Kodein.OverridingException](../../-kodein/-overriding-exception/index.md).

### Exceptions

`Kodein.OverridingException` - If this kodein overrides an existing binding and is not allowed to
OR [allowOverride](extend.md#com.github.salomonbrys.kodein.KodeinContainer.Builder$extend(com.github.salomonbrys.kodein.KodeinContainer, kotlin.Boolean)/allowOverride) is true while YOU dont have the permission to override.