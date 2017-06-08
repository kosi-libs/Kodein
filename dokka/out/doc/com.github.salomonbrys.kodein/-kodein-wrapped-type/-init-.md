[com.github.salomonbrys.kodein](../index.md) / [KodeinWrappedType](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`KodeinWrappedType(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`

Wraps a ParameterizedType and implements hashCode / equals.

This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
ParameterizedType (I know...).

