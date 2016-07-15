[com.github.salomonbrys.kodein.global](.)

## Package com.github.salomonbrys.kodein.global

Kodein API extensions for a static One True Kodein.

### Types

| Name | Summary |
|---|---|
| [GlobalKodein](-global-kodein/index.md) | `class GlobalKodein : `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)<br>A class that can be used to configure a kodein object and as a kodein object. |
| [KodeinGlobalAware](-kodein-global-aware/index.md) | `interface KodeinGlobalAware : `[`KodeinAware`](../com.github.salomonbrys.kodein/-kodein-aware.md)<br>A `KodeinAware` class that needs no implementation because the kodein used will be the [global](global.md) One True Kodein. |

### Companion Object Properties

| Name | Summary |
|---|---|
| [global](global.md) | `val Kodein.Companion.global: `[`GlobalKodein`](-global-kodein/index.md)<br>A global One True Kodein. |
