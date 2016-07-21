[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [mutateAddConfig](.)

# mutateAddConfig

`fun mutateAddConfig(config: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.() -> Unit): Unit`

Adds a configuration to the bindings that will extend the existing bindings.

### Parameters

`config` - The lambda to be applied when the kodein instance is constructed.

### Exceptions

`IllegalStateException` - if [mutable](mutable.md) is not `true`.