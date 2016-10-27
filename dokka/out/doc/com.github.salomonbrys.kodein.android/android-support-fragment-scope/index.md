[com.github.salomonbrys.kodein.android](../index.md) / [androidSupportFragmentScope](.)

# androidSupportFragmentScope

`object androidSupportFragmentScope : `[`Scope`](../../com.github.salomonbrys.kodein/-scope/index.md)`<Fragment>`

Androids support fragment scope. Allows to register support fragment-specific singletons.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `fun getRegistry(context: Fragment): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given support fragment. Will always return the same registry for the same support fragment. |
