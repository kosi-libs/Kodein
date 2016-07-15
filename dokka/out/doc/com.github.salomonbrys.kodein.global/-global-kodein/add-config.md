[com.github.salomonbrys.kodein.global](../index.md) / [GlobalKodein](index.md) / [addConfig](.)

# addConfig

`fun addConfig(config: `[`Builder`](../../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.() -> Unit): Unit`

Adds a configuration to the Kodein construction that will be applied when it is constructed.

### Parameters

`config` - The lambda to be applied when the kodein instance is constructed.

### Exceptions

`IllegalStateException` - When calling this function after [getOrConstruct](get-or-construct.md) or any `Kodein` retrieval function.