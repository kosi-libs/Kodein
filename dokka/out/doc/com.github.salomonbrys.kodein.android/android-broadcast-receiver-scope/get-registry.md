[com.github.salomonbrys.kodein.android](../index.md) / [androidBroadcastReceiverScope](index.md) / [getRegistry](.)

# getRegistry

`fun getRegistry(context: BroadcastReceiver): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)

Get a registry for a given broadcast receiver. Will always return the same registry for the same broadcast receiver.

### Parameters

`context` - The broadcast receiver associated with the returned registry.

**Return**
The registry associated with the given broadcast receiver.

