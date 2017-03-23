[com.github.salomonbrys.kodein.android](../index.md) / [androidServiceScope](.)

# androidServiceScope

`object androidServiceScope : `[`AndroidScope`](../-android-scope/index.md)`<Service>`

Android's service scope. Allows to register service-specific singletons.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `fun getRegistry(context: Service): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given service. Will always return the same registry for the same service. |
| [removeFromScope](remove-from-scope.md) | `fun removeFromScope(context: Service): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Allows for cleaning up after a service has been destroyed |
