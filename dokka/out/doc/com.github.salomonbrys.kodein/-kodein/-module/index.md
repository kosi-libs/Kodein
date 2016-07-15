[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Module](.)

# Module

`class Module`

A module is constructed the same way as in [Kodein](../index.md) is:

```
val module = Kodein.Module {
    bind&lt;DataSource&gt;() with singleton { SqliteDS.open("path/to/file") }
}
```

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Module(allowSilentOverride: Boolean = false, init: `[`Builder`](../-builder/index.md)`.() -> Unit)`<br>A module is constructed the same way as in [Kodein](../index.md) is: |

### Properties

| Name | Summary |
|---|---|
| [allowSilentOverride](allow-silent-override.md) | `val allowSilentOverride: Boolean`<br>Whether this module is allowed to non-explicit overrides. |
| [init](init.md) | `val init: `[`Builder`](../-builder/index.md)`.() -> Unit`<br>The block of configuration for this module. |
