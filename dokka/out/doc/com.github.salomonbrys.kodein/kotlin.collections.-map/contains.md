[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [contains](.)

# contains

`operator fun `[`BindingsMap`](../-bindings-map.md)`.contains(bind: `[`Bind`](../-kodein/-bind/index.md)`<*>): Boolean`

### Parameters

`bind` - The bind to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
Whether or not this binding can be found in the binding map, whatever the factory argument type.

`operator fun `[`BindingsMap`](../-bindings-map.md)`.contains(type: `[`TypeToken`](../-type-token/index.md)`<*>): Boolean`

### Parameters

`type` - The type to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
Whether or not this type is bound in the binding map, whatever the tag or the factory argument type.

