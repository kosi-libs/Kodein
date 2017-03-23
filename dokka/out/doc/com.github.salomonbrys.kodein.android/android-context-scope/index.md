[com.github.salomonbrys.kodein.android](../index.md) / [androidContextScope](.)

# androidContextScope

`object androidContextScope : `[`Scope`](../../com.github.salomonbrys.kodein/-scope/index.md)`<Context>`

Android's context scope. Allows to register context-specific singletons.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `fun getRegistry(context: Context): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given context. Will always return the same registry for the same context. |
