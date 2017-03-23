[com.github.salomonbrys.kodein](../index.md) / [kotlin.collections.Map](index.md) / [types](.)

# types

`fun Map<`[`Key`](../-kodein/-key/index.md)`, `[`Factory`](../-factory/index.md)`<*, *>>.types(tag: Any?): List<`[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`>`

### Parameters

`tag` - The tag to look for.

**Receiver**
The bindings map, obtained with [KodeinContainer.bindings](../-kodein-container/bindings.md).

**Return**
The list of types that are bound with this tag. Each entry represents a different [Kodein.Bind](../-kodein/-bind/index.md),
    but there may be multiple [Kodein.Key](../-kodein/-key/index.md) for the same [Kodein.Bind](../-kodein/-bind/index.md).

