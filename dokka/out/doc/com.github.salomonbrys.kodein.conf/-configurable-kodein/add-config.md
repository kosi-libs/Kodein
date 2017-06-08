[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [addConfig](.)

# addConfig

`fun addConfig(config: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.() -> Unit): Unit`

Adds a configuration to the bindings that will be applied when the Kodein is constructed.

### Parameters

`config` - The lambda to be applied when the kodein instance is constructed.

### Exceptions

`IllegalStateException` - When calling this function after [getOrConstruct](get-or-construct.md) or any `Kodein` retrieval function.