[com.github.salomonbrys.kodein.android](index.md) / [activitySingleton](.)

# activitySingleton

`inline fun <reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.activitySingleton(noinline creator: `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.(Activity) -> T): `[`Factory`](../com.github.salomonbrys.kodein/-factory/index.md)`<Activity, T>`

Creates an activity scoped singleton factory, effectively a `factory { Activity -> T }`.

### Parameters

`T` - The singleton type.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist for the activity argument.

**Return**
The factory to bind.

