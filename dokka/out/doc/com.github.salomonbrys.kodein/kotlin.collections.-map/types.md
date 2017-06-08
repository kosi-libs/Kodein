[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [types](.)

# types

`fun `[`BindingsMap`](../-bindings-map.md)`.types(tag: Any?): List<`[`TypeToken`](../-type-token/index.md)`<*>>`

### Parameters

`tag` - The tag to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
The list of types that are bound with this tag. Each entry represents a different [Kodein.Bind](../-kodein/-bind/index.md),
    but there may be multiple [Kodein.Key](../-kodein/-key/index.md) for the same [Kodein.Bind](../-kodein/-bind/index.md).

