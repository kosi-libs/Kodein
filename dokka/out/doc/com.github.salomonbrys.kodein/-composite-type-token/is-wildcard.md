[com.github.salomonbrys.kodein](../index.md) / [CompositeTypeToken](index.md) / [isWildcard](.)

# isWildcard

`fun isWildcard(): Boolean`

Overrides [TypeToken.isWildcard](../-type-token/is-wildcard.md)

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

