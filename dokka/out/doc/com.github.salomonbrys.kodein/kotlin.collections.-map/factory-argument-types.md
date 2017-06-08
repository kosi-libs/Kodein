[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [factoryArgumentTypes](.)

# factoryArgumentTypes

`fun `[`BindingsMap`](../-bindings-map.md)`.factoryArgumentTypes(bind: `[`Bind`](../-kodein/-bind/index.md)`<*>): List<`[`TypeToken`](../-type-token/index.md)`<*>>`

### Parameters

`bind` - The bind to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
The list of argument type that are bound with this binding. Each entry represents a different [Kodein.Key](../-kodein/-key/index.md).

