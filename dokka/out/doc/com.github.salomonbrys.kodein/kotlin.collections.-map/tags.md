[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [tags](.)

# tags

`fun `[`BindingsMap`](../-bindings-map.md)`.tags(type: `[`TypeToken`](../-type-token/index.md)`<*>): List<Any?>`

### Parameters

`type` - The type to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
The list of tags that are bound with this type. Each entry represents a different [Kodein.Bind](../-kodein/-bind/index.md),
    but there may be multiple [Kodein.Key](../-kodein/-key/index.md) for the same [Kodein.Bind](../-kodein/-bind/index.md).

