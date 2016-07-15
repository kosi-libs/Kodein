[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Builder](index.md) / [import](.)

# import

`fun import(module: `[`Module`](../-module/index.md)`, allowOverride: Boolean = false): Unit`

Imports all bindings defined in the given [Kodein.Module](../-module/index.md) into this builders definition.

Note that modules are *definitions*, they will re-declare their bindings in each kodein instance you use.

### Parameters

`module` - The module object to import.

`allowOverride` - Whether this module is allowed to override existing bindings.
If it is not, overrides (even explicit) will throw an [OverridingException](../-overriding-exception/index.md).

### Exceptions

`OverridingException` - If this module overrides an existing binding and is not allowed to
OR [allowOverride](import.md#com.github.salomonbrys.kodein.Kodein.Builder$import(com.github.salomonbrys.kodein.Kodein.Module, kotlin.Boolean)/allowOverride) is true while YOU dont have the permission to override.