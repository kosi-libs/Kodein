[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [addExtend](.)

# addExtend

`fun addExtend(kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`, allowOverride: Boolean = false): Unit`

Adds the bindings of an existing kodein instance to the bindings that will be applied when the Kodein is constructed.

### Parameters

`kodein` - The existing kodein instance whose bindings to be apply when the kodein instance is constructed.

`allowOverride` - Whether these bindings are allowed to override existing bindings.

### Exceptions

`IllegalStateException` - When calling this function after [getOrConstruct](get-or-construct.md) or any `Kodein` retrieval function.