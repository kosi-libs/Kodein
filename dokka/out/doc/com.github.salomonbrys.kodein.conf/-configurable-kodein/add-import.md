[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [addImport](.)

# addImport

`fun addImport(module: `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)`, allowOverride: Boolean = false): Unit`

Adds a module to the bindings that will be applied when the Kodein is constructed.

### Parameters

`module` - The module to apply when the kodein instance is constructed.

`allowOverride` - Whether this module is allowed to override existing bindings.

### Exceptions

`IllegalStateException` - When calling this function after [getOrConstruct](get-or-construct.md) or any `Kodein` retrieval function.