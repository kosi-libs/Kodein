[com.github.salomonbrys.kodein.android](../index.md) / [androidActivityScope](.)

# androidActivityScope

`object androidActivityScope : `[`AndroidScope`](../-android-scope/index.md)`<Activity>, `[`AutoScope`](../../com.github.salomonbrys.kodein/-auto-scope/index.md)`<Activity>`

Android's activity scope. Allows to register activity-specific singletons.

Scope that can be used both as a scope or as an auto-scope.

/!\ If used as an auto-scope, you need to register the [lifecycleManager](lifecycle-manager.md).

### Types

| Name | Summary |
|---|---|
| [lifecycleManager](lifecycle-manager.md) | `object lifecycleManager : ActivityLifecycleCallbacks`<br>If you use [autoActivitySingleton](../auto-activity-singleton.md), you **must** register this lifecycle manager in your application's oncreate: |

### Functions

| Name | Summary |
|---|---|
| [getContext](get-context.md) | `fun getContext(): Activity` |
| [getRegistry](get-registry.md) | `fun getRegistry(context: Activity): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given activity. Will always return the same registry for the same activity. |
| [removeFromScope](remove-from-scope.md) | `fun removeFromScope(context: Activity): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Allows for cleaning up after an activity has been destroyed |
