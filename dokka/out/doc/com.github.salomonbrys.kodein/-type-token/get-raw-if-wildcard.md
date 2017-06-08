[com.github.salomonbrys.kodein](../index.md) / [TypeToken](index.md) / [getRawIfWildcard](.)

# getRawIfWildcard

`abstract fun getRawIfWildcard(): `[`TypeToken`](index.md)`<T>?`

Returns the raw type if the type represented by this TypeToken is generic and is entirely wildcard.

Examples:

* `List<*>`: true
* `List<String>`: false
* `Map<*, *>`: true
* `Map<String, *>`: false
* `Map<*, String>`: false
* `Map<String, String>`: very false!

**Return**
the raw type if the type represented by this TypeToken is generic and is entirely wildcard, otherwise null.

