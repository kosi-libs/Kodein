[com.github.salomonbrys.kodein](../../../index.md) / [Kodein](../../index.md) / [Builder](../index.md) / [TypeBinder](.)

# TypeBinder

`class TypeBinder<T : Any>`

Left part of the type-binding syntax (`bind(type, tag)`).

### Parameters

`T` - The type to bind.

### Functions

| Name | Summary |
|---|---|
| [with](with.md) | `infix fun with(binding: `[`Binding`](../../../../com.github.salomonbrys.kodein.bindings/-binding/index.md)`<*, out T>): Unit`<br>Binds the previously given type and tag to the given binding. |

### Extension Functions

| Name | Summary |
|---|---|
| [InSet](../../../../com.github.salomonbrys.kodein.bindings/-in-set.md) | `fun <T : Any> TypeBinder<T>.InSet(setTypeToken: `[`TypeToken`](../../../-type-token/index.md)`<Set<T>>): `[`TypeBinderInSet`](../../../../com.github.salomonbrys.kodein.bindings/-type-binder-in-set/index.md)`<T, Set<T>>`<br>Allows to bind in an existing set binding (rather than directly as a new binding). |
| [inSet](../../../in-set.md) | `fun <T : Any> TypeBinder<T>.inSet(): `[`TypeBinderInSet`](../../../../com.github.salomonbrys.kodein.bindings/-type-binder-in-set/index.md)`<T, Set<T>>`<br>Defines that the binding will be saved in a set binding. |
| [inSet](../../../../com.github.salomonbrys.kodein.erased/in-set.md) | `fun <T : Any> TypeBinder<T>.inSet(): `[`TypeBinderInSet`](../../../../com.github.salomonbrys.kodein.bindings/-type-binder-in-set/index.md)`<T, Set<T>>`<br>Defines that the binding will be saved in a set binding. |
