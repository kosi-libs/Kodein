[com.github.salomonbrys.kodein](../../index.md) / [KodeinContainer](../index.md) / [Builder](index.md) / [subBuilder](.)

# subBuilder

`fun subBuilder(allowOverride: Boolean = false, silentOverride: Boolean = false): `[`Builder`](index.md)

Creates a sub builder that will register its bindings to the same map.

### Parameters

`allowOverride` - Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.

`silentOverride` - Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.