[com.github.salomonbrys.kodein](../index.md) / [Scope](.)

# Scope

`interface Scope<in C>`

An object capable of providing a [ScopeRegistry](../-scope-registry/index.md) for a given `C` context.

### Parameters

`C` - The type of the context that will be used to retrieve the registry.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `abstract fun getRegistry(context: C): `[`ScopeRegistry`](../-scope-registry/index.md)<br>Get a registry for a given context. Should always return the same registry for the same context. |

### Inheritors

| Name | Summary |
|---|---|
| [AutoScope](../-auto-scope/index.md) | `interface AutoScope<C> : Scope<C>`<br>An object that can, in addition to being a regular scope, can also get a context from a static environment. |
| [androidBroadcastReceiverScope](../../com.github.salomonbrys.kodein.android/android-broadcast-receiver-scope/index.md) | `object androidBroadcastReceiverScope : Scope<BroadcastReceiver>`<br>Androids broadcast receiver scope. Allows to register broadcast receiver-specific singletons. |
| [androidContextScope](../../com.github.salomonbrys.kodein.android/android-context-scope/index.md) | `object androidContextScope : Scope<Context>`<br>Androids context scope. Allows to register context-specific singletons. |
| [androidFragmentScope](../../com.github.salomonbrys.kodein.android/android-fragment-scope/index.md) | `object androidFragmentScope : Scope<Fragment>`<br>Androids fragment scope. Allows to register fragment-specific singletons. |
| [androidServiceScope](../../com.github.salomonbrys.kodein.android/android-service-scope/index.md) | `object androidServiceScope : Scope<Service>`<br>Androids service scope. Allows to register service-specific singletons. |
| [androidSupportFragmentScope](../../com.github.salomonbrys.kodein.android/android-support-fragment-scope/index.md) | `object androidSupportFragmentScope : Scope<Fragment>`<br>Androids support fragment scope. Allows to register support fragment-specific singletons. |
