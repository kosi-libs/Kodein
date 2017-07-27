[com.github.salomonbrys.kodein](../index.md) / [CompositeTypeToken](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CompositeTypeToken(main: `[`TypeToken`](../-type-token/index.md)`<T>, vararg params: `[`TypeToken`](../-type-token/index.md)`<*>)`

A composite type token represents a generic class in an erased manner.

For example, the type `Map<String, List<String>>` can be represented as:

```
CompositeTypeToken(erased<Map<*, *>>(), erased<String>(), CompositeTypeToken(erased<List<*>(), erased<String>()))
```

Note that you should rather use the [erasedComp1](../erased-comp1.md), [erasedComp2](../erased-comp2.md) or [erasedComp3](../erased-comp3.md) functions to create a composite type token.

### Parameters

`T` - The main type represented by this type token.