[com.github.salomonbrys.kodein](../index.md) / [KodeinWrappedType](.)

# KodeinWrappedType

`class KodeinWrappedType : `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)

Wraps a ParameterizedType and implements hashCode / equals.

This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
ParameterizedType (I know...).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinWrappedType(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>Wraps a ParameterizedType and implements hashCode / equals. |

### Properties

| Name | Summary |
|---|---|
| [type](type.md) | `val type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type object to wrap. |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): String`<br>Stringify. |

### Extension Functions

| Name | Summary |
|---|---|
| [fullDispString](../java.lang.reflect.-type/full-disp-string.md) | `fun `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`.fullDispString(): String`<br>A string representing this type in a Kotlin-esque fashion using full type names. |
| [simpleDispString](../java.lang.reflect.-type/simple-disp-string.md) | `fun `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`.simpleDispString(): String`<br>A string representing this type in a Kotlin-esque fashion using simple type names. |
