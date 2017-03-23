[com.github.salomonbrys.kodein.android](../index.md) / [androidActivityScope](index.md) / [lifecycleManager](.)

# lifecycleManager

`object lifecycleManager : ActivityLifecycleCallbacks`

If you use [autoActivitySingleton](../auto-activity-singleton.md), you **must** register this lifecycle manager in your application's oncreate:

``` kotlin
class MyActivity : Activity {
    override fun onCreate() {
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }
}
```

