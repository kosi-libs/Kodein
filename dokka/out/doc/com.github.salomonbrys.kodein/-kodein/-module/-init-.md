[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Module](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`Module(allowSilentOverride: Boolean = false, init: `[`Builder`](../-builder/index.md)`.() -> Unit)`

A module is constructed the same way as in [Kodein](../index.md) is:

```
val module = Kodein.Module {
    bind&lt;DataSource&gt;() with singleton { SqliteDS.open("path/to/file") }
}
```

