[com.github.salomonbrys.kodein](../index.md) / [AutoScope](.)

# AutoScope

`interface AutoScope<C> : `[`Scope`](../-scope/index.md)`<C>`

An object that can, in addition to being a regular scope, can also get a context from a static environment.

### Parameters

`C` - The type of the context that can be statically retrieved.

### Functions

| Name | Summary |
|---|---|
| [getContext](get-context.md) | `abstract fun getContext(): C`<br>Get the context according to the static environment. |

### Inheritors

| Name | Summary |
|---|---|
| [androidActivityScope](../../com.github.salomonbrys.kodein.android/android-activity-scope/index.md) | `object androidActivityScope : `[`AndroidScope`](../../com.github.salomonbrys.kodein.android/-android-scope/index.md)`<Activity>, AutoScope<Activity>`<br>Android's activity scope. Allows to register activity-specific singletons. |
