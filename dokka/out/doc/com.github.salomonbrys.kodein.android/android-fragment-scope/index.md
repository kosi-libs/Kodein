[com.github.salomonbrys.kodein.android](../index.md) / [androidFragmentScope](.)

# androidFragmentScope

`object androidFragmentScope : `[`AndroidScope`](../-android-scope/index.md)`<Fragment>`

Android's fragment scope. Allows to register fragment-specific singletons.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `fun getRegistry(context: Fragment): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given fragment. Will always return the same registry for the same fragment. |
| [removeFromScope](remove-from-scope.md) | `fun removeFromScope(context: Fragment): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Allows for cleaning up after a fragment has been destroyed |
