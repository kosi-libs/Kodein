[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Module](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`Module(allowSilentOverride: Boolean = false, init: `[`Builder`](../-builder/index.md)`.() -> Unit)`

A module is constructed the same way as in [Kodein](../index.md) is:

``` kotlinprivate
val module = Kodein.Module {
    bind<DataSource>() with singleton { SqliteDS.open("path/to/file") }
}
```

