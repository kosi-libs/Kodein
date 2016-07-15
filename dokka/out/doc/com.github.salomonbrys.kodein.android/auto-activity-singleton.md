[com.github.salomonbrys.kodein.android](index.md) / [autoActivitySingleton](.)

# autoActivitySingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.autoActivitySingleton(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(Activity) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<Unit, T>`

Creates an activity auto-scoped singleton factory, effectively a `provider { -> T }`.

Note that, to use this, you **must** register the [activityScope.lifecycleManager](activity-scope/lifecycle-manager.md).

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the activity argument.

**Return**
The provider to bind.

