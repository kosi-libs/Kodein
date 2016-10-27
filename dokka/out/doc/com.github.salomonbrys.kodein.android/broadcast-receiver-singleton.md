[com.github.salomonbrys.kodein.android](index.md) / [broadcastReceiverSingleton](.)

# broadcastReceiverSingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.~~broadcastReceiverSingleton~~(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(BroadcastReceiver) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<BroadcastReceiver, T>`
**Deprecated:** Use scopedSingleton instead.

Creates a broadcast receiver scoped singleton factory, effectively a `factory { BroadcastReceiver -> T }`.

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the broadcast receiver argument.

**Return**
The factory to bind.

