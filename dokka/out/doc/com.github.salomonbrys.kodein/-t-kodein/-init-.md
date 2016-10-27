[com.github.salomonbrys.kodein](../index.md) / [TKodein](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`TKodein(_container: `[`KodeinContainer`](../-kodein-container/index.md)`)`

Typed access to Kodein dependency injection. Can be used in Java.

Each method works either with a [TypeToken](../-type-token/index.md), a `Class` or a [Type](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html).

In Java, to create a [TypeToken](../-type-token/index.md), you should use the following syntax: `new TypeReference<Type<SubType>>(){}`.
In Kotlin, simply use the [genericToken](../generic-token.md) function.

This class contains utility functions that will all eventually use the associated [KodeinContainer](../-kodein-container/index.md)

