[com.github.salomonbrys.kodein](../../index.md) / [KodeinContainer](../index.md) / [Builder](.)

# Builder

`class Builder`

This is where you configure the bindings.

### Parameters

`allowOverride` - Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.

`silentOverride` - Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.

### Types

| Name | Summary |
|---|---|
| [BindBinder](-bind-binder/index.md) | `inner class BindBinder<T : Any>`<br>Left part of the bind-binding syntax (`bind(Kodein.Bind(type, tag))`). |
| [KeyBinder](-key-binder/index.md) | `inner class KeyBinder<A, T : Any>`<br>Left part of the key-binding syntax (`bind(Kodein.Key(Kodein.Bind(type, tag), argType))`). |

### Functions

| Name | Summary |
|---|---|
| [bind](bind.md) | `fun <A, T : Any> bind(key: `[`Key`](../../-kodein/-key/index.md)`<A, T>, overrides: Boolean? = null): `[`KeyBinder`](-key-binder/index.md)`<A, T>`<br>Starts the binding of a given key.`fun <T : Any> bind(bind: `[`Bind`](../../-kodein/-bind/index.md)`<T>, overrides: Boolean? = null): `[`BindBinder`](-bind-binder/index.md)`<T>`<br>Starts the binding of a given type and tag. |
| [extend](extend.md) | `fun extend(container: `[`KodeinContainer`](../index.md)`, allowOverride: Boolean = false): Unit`<br>Imports all bindings defined in the given [KodeinContainer](../index.md) into this builder. |
| [subBuilder](sub-builder.md) | `fun subBuilder(allowOverride: Boolean = false, silentOverride: Boolean = false): Builder`<br>Creates a sub builder that will register its bindings to the same map. |
