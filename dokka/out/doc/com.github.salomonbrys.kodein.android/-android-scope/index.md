[com.github.salomonbrys.kodein.android](../index.md) / [AndroidScope](.)

# AndroidScope

`interface AndroidScope<in T> : `[`Scope`](../../com.github.salomonbrys.kodein/-scope/index.md)`<T>`

Base interface from all Android scopes.

### Parameters

`T` - The type of context of the scope.

### Functions

| Name | Summary |
|---|---|
| [removeFromScope](remove-from-scope.md) | `abstract fun removeFromScope(context: T): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Removes a context from the scope to prevent its leak. |

### Inheritors

| Name | Summary |
|---|---|
| [androidActivityScope](../android-activity-scope/index.md) | `object androidActivityScope : AndroidScope<Activity>, `[`AutoScope`](../../com.github.salomonbrys.kodein/-auto-scope/index.md)`<Activity>`<br>Androids activity scope. Allows to register activity-specific singletons. |
| [androidBroadcastReceiverScope](../android-broadcast-receiver-scope/index.md) | `object androidBroadcastReceiverScope : AndroidScope<BroadcastReceiver>`<br>Androids broadcast receiver scope. Allows to register broadcast receiver-specific singletons. |
| [androidFragmentScope](../android-fragment-scope/index.md) | `object androidFragmentScope : AndroidScope<Fragment>`<br>Androids fragment scope. Allows to register fragment-specific singletons. |
| [androidServiceScope](../android-service-scope/index.md) | `object androidServiceScope : AndroidScope<Service>`<br>Androids service scope. Allows to register service-specific singletons. |
| [androidSupportFragmentScope](../android-support-fragment-scope/index.md) | `object androidSupportFragmentScope : AndroidScope<Fragment>`<br>Androids support fragment scope. Allows to register support fragment-specific singletons. |
