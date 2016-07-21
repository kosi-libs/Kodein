[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [mutateAddExtend](.)

# mutateAddExtend

`fun mutateAddExtend(kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`, allowOverride: Boolean = false): Unit`

Adds the bindings of an existing kodein instance that will extend the existing bindings.

### Parameters

`kodein` - The existing kodein instance whose bindings to be apply when the kodein instance is constructed.

`allowOverride` - Whether these bindings are allowed to override existing bindings.

### Exceptions

`IllegalStateException` - When calling this function after [getOrConstruct](get-or-construct.md) or any `Kodein` retrieval function.