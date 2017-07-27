[com.github.salomonbrys.kodein](../index.md) / [TypeToken](index.md) / [isWildcard](.)

# isWildcard

`abstract fun isWildcard(): Boolean`

Returns whether the type represented by this TypeToken is generic and is entirely wildcard.

Examples:

* `List<*>`: true
* `List<String>`: false
* `Map<*, *>`: true
* `Map<String, *>`: false
* `Map<*, String>`: false
* `Map<String, String>`: very false!

**Return**
whether the type represented by this TypeToken is generic and is entirely wildcard, otherwise null.

