[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [tags](.)

# tags

`fun Map<`[`Key`](../-kodein/-key/index.md)`, `[`Factory`](../-factory/index.md)`<*, *>>.tags(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`): List<Any?>`
`fun Map<`[`Key`](../-kodein/-key/index.md)`, `[`Factory`](../-factory/index.md)`<*, *>>.~~tags~~(type: `[`TypeToken`](../-type-token/index.md)`<*>): List<Any?>`
**Deprecated:** Use contains(Type) instead.

### Parameters

`type` - The type to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
The list of tags that are bound with this type. Each entry represents a different [Kodein.Bind](../-kodein/-bind/index.md),
    but there may be multiple [Kodein.Key](../-kodein/-key/index.md) for the same [Kodein.Bind](../-kodein/-bind/index.md).

