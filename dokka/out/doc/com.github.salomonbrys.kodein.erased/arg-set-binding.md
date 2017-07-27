[com.github.salomonbrys.kodein.erased](index.md) / [argSetBinding](.)

# argSetBinding

`inline fun <reified A, reified T : Any> `[`Builder`](../com.github.salomonbrys.kodein/-kodein/-builder/index.md)`.argSetBinding(): `[`ArgSetBinding`](../com.github.salomonbrys.kodein.bindings/-arg-set-binding/index.md)`<A, T>`

Creates a set: multiple bindings can be added in this set.

A &amp; T generics will be erased!

### Parameters

`A` - The argument type.

`T` - The created type.

**Return**
A set binding ready to be bound.

