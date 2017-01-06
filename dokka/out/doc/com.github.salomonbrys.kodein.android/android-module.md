[com.github.salomonbrys.kodein.android](index.md) / [androidModule](.)

# androidModule

`val androidModule: `[`Module`](../com.github.salomonbrys.kodein/-kodein/-module/index.md)

A module that binds a lot of Android framework classes:

```
class MyActivity : Activity(), KodeinInjected {
  override val injector = KodeinInjector()
  override val inflator: LayoutInflator by withContext(this).instance()
}
```

