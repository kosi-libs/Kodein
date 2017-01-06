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
| [AndroidScope](../../com.github.salomonbrys.kodein.android/-android-scope/index.md) | `interface AndroidScope<in T> : Scope<T>`<br>Base interface from all Android scopes. |
| [AutoScope](../-auto-scope/index.md) | `interface AutoScope<C> : Scope<C>`<br>An object that can, in addition to being a regular scope, can also get a context from a static environment. |
| [androidContextScope](../../com.github.salomonbrys.kodein.android/android-context-scope/index.md) | `object androidContextScope : Scope<Context>`<br>Androids context scope. Allows to register context-specific singletons. |
