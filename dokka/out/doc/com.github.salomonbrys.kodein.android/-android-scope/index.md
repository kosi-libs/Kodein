[com.github.salomonbrys.kodein.android](../index.md) / [AndroidScope](.)

# AndroidScope

`interface AndroidScope<in T> : `[`Scope`](../../com.github.salomonbrys.kodein/-scope/index.md)`<T>`

Base interface from all Android scopes.

### Parameters

`T` - The type of context of the scope.

### Functions

| Name | Summary |
|---|---|
| [removeFromScope](remove-from-scope.md) | `abstract fun removeFromScope(context: T): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Removes a context from the scope to prevent it's leak. |

### Inheritors

| Name | Summary |
|---|---|
| [androidActivityScope](../android-activity-scope/index.md) | `object androidActivityScope : AndroidScope<Activity>, `[`AutoScope`](../../com.github.salomonbrys.kodein/-auto-scope/index.md)`<Activity>`<br>Android's activity scope. Allows to register activity-specific singletons. |
| [androidBroadcastReceiverScope](../android-broadcast-receiver-scope/index.md) | `object androidBroadcastReceiverScope : AndroidScope<BroadcastReceiver>`<br>Android's broadcast receiver scope. Allows to register broadcast receiver-specific singletons. |
| [androidFragmentScope](../android-fragment-scope/index.md) | `object androidFragmentScope : AndroidScope<Fragment>`<br>Android's fragment scope. Allows to register fragment-specific singletons. |
| [androidServiceScope](../android-service-scope/index.md) | `object androidServiceScope : AndroidScope<Service>`<br>Android's service scope. Allows to register service-specific singletons. |
| [androidSupportFragmentScope](../android-support-fragment-scope/index.md) | `object androidSupportFragmentScope : AndroidScope<Fragment>`<br>Android's support fragment scope. Allows to register support fragment-specific singletons. |
