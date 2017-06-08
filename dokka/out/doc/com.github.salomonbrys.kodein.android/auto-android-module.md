[com.github.salomonbrys.kodein.android](index.md) / [autoAndroidModule](.)

# autoAndroidModule

`fun autoAndroidModule(app: Application): `[`Module`](../com.github.salomonbrys.kodein/-kodein/-module/index.md)

A module that binds a lot of Android framework classes:

``` kotlin
class MyActivity : Activity(), KodeinInjected {
  override val injector = KodeinInjector()
  override val inflator: LayoutInflator by instance()
}
```

