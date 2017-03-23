[com.github.salomonbrys.kodein.android](../index.md) / [androidBroadcastReceiverScope](.)

# androidBroadcastReceiverScope

`object androidBroadcastReceiverScope : `[`AndroidScope`](../-android-scope/index.md)`<BroadcastReceiver>`

Android's broadcast receiver scope. Allows to register broadcast receiver-specific singletons.

### Functions

| Name | Summary |
|---|---|
| [getRegistry](get-registry.md) | `fun getRegistry(context: BroadcastReceiver): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)<br>Get a registry for a given broadcast receiver. Will always return the same registry for the same broadcast receiver. |
| [removeFromScope](remove-from-scope.md) | `fun removeFromScope(context: BroadcastReceiver): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Allows for cleaning up after a broadcast receiver has been destroyed |
