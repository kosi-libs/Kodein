[com.github.salomonbrys.kodein.android](../index.md) / [activityScope](index.md) / [lifecycleManager](.)

# lifecycleManager

`object lifecycleManager : ActivityLifecycleCallbacks`

If you use [autoActivitySingleton](../auto-activity-singleton.md), you **must** register this lifecycle manager in your applications oncreate:

```
class MyActivity : Activity {
    override fun onCreate() {
        registerActivityLifecycleCallbacks(activityScope.lifecycleManager)
    }
}
```

