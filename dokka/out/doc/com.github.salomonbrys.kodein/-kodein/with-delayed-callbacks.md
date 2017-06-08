[com.github.salomonbrys.kodein](../index.md) / [Kodein](index.md) / [withDelayedCallbacks](.)

# withDelayedCallbacks

`fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: `[`Builder`](-builder/index.md)`.() -> Unit): Pair<`[`Kodein`](index.md)`, () -> Unit>`

Creates a Kodein object but without directly calling onReady callbacks.

Instead, returns both the kodein instance and the callbacks.
Note that the returned kodein object should not be used before calling the callbacks.

This is an internal function that exists primarily to prevent Kodein.global recursion.

